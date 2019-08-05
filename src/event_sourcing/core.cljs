(ns event-sourcing.core
  (:require [cljs.reader]))

(println "Hello world!" (.now js/Date))


(def fs (js/require "fs"))
(defn write-to-file [event filename]
  ;;(prn "writing " (assoc (js->clj (cljs.reader/read-string (str (clj->js event)))) :test "test") " to file")
  (.appendFile fs filename (clj->js (str event))
              (fn [err] (if err
                          (println "error:" err)))))

(def event-store "events.txt")
;;this is not a map, so I cannot for now assoc onto it. the string needs to be converted to a map
(defn record-event [event]
  (prn (assoc event :timestamp (.now js/Date)))
  (-> event
      (assoc :timestamp (.now js/Date))
      (write-to-file event-store)))

(defmulti apply-event
  (fn [projection event] (:event-type event)))

(defmethod apply-event :record-reading [projection event]
  event)

(defmethod apply-event :create-account [projection event]
  (assoc projection :username (get-in event [:data :user])))

(defmethod apply-event :add-reading [projection event]
  (let [readings (:reading projection)
        event    (select-keys event [:data])]
    (assoc projection :readings (conj readings event))))

(record-event {:event-type :create-account :data {:user "a.pitendrigh@pm.me"}})
(record-event {:event-type :add-reading :data {:user "a.pittendrigh@pm.me" :meter-reading 1000}})

(prn "final ---->"
 (-> {}
     (apply-event {:event-type :create-account :data {:user "a.pitendrigh@pm.me"}})
     (apply-event {:event-type :add-reading :data {:user "a.pittendrigh@pm.me" :meter-reading 1000}})))
