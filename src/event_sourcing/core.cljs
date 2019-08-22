(ns event-sourcing.core
  (:require [cljs.reader]))

(println "Hello world!" (.now js/Date))

(def fs (js/require "fs"))

(defn parse-event-log [events]
  (prn "in  parase")
  (cljs.reader/read-string events))

(defn read-event-log [filename callback]
  (let [default-callback (fn [err data]
                           (if err
                             (prn "Something went wrong" err)
                             (prn (count (:events (parse-event-log data))))))
        callback (or callback default-callback)]
      (.readFile fs filename "utf8" callback)))

(defn write-to-file [event filename]
  (read-event-log filename
                  (fn [err data]
                    (prn "here")
                    (if err
                      (prn "Something went wrong while reading the event log; " err)
                      (do
                        (prn "here 2")
                        (let [events (parse-event-log data)
                            events (conj events event)
                            events (str events)]
                        (.writeFile fs filename events
                                     (fn [err]
                                       (if err
                                         (println
                                          "Something went wrong writing the event log: " err))))))))))

(def event-store "events.txt")

(comment
  (read-event-log event-store)
  (count (read-event-log event-store))
  )

(defn record-event [event]
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

(let [event {:hello "there"}
      events {:events [event event]}]
  ;;(str (clj->js event))
  (cljs.reader/read-string (str events))
  (assoc (cljs.reader/read-string (str event)) :test "test")
  ;;(clj->js event)
  (str events)
  (count (:events (cljs.reader/read-string (str events))))

  (read-event-log event-store (fn [err data]
                                (if err
                                  (prn "Something wen't wrong" err)
                                  (-> data
                                      (prn)))))
  
  (.readFile fs event-store "utf8" (fn [err data]
                                  (if err
                                      (prn "Something wen't wrong" err)
                                      (prn "Read the file ->" data))))

  (-> {:event-type :create-account :data {:user "a.pitendrigh@pm.me"}}
      (assoc :timestamp (.now js/Date))
      #_(prn)
      (write-to-file event-store))
  )

(prn "Connected!")
