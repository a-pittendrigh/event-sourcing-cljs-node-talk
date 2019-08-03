(ns event-sourcing.core)

(println "Hello world!")

;; ADDED
(defn average [a b]
  (/ (+ a b) 2.0))

(defn record-event [event]
  )

(defmulti apply-event
  (fn [event] (:event-type event)))

(defmethod apply-event :record-reading [event]
  )

(defmethod apply-event :create-account [event] )


;; (spit "abc.txt" "hello")
;; const fs = require('fs')

;; const content = 'Some content!'



(def fs (js/require "fs"))

;; fs.writeFile('/Users/joe/test.txt', content, err => {
;;                                                      if (err) {
;;                                                                console.error(err)
;;                                                                return
;;                                                                }
;;                                                      //file written successfully
;;                                                      })

(defn write-to-file [content filename]
  (.writeFile fs filename content
              (fn [err] (if err
                         (println "error: " err)))))

(write-to-file "Hello, World!" "test.txt")
