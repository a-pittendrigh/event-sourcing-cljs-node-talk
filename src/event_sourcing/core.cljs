(ns event-sourcing.core)

(println "Hello world!" (.now js/Date))

;; ADDED
(defn average [a b]
  (/ (+ a b) 2.0))



(def fs (js/require "fs"))
(defn write-to-file [event filename]
  ;; (prn "writing " event " to file")
  (.writeFile fs filename (clj->js (str event))
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
  (fn [event] (:event-type event)))

(defmethod apply-event :record-reading [event]
  )

(defmethod apply-event :create-account [event]
  )

;;(record-event {:event-type :create-account :data {:user "a.pitendrigh@pm.me"}})
(record-event {:event-type :add-reading :data {:user "a.pittendrigh@pm.me" :meter-reading 1000}})
