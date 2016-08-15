(ns markov-twooter.core
  (:require [markov-twooter.twitter-interface :as twit :refer [status-update]]
                [overtone.at-at :as overtone])
  (:gen-class))

(def timing-pool (overtone/mk-pool))

(defn -main
  "Public interface for markov-generator."
  [& args]
  (println "Starting and reading source files")
  (overtone/every (* 1000 60 60 8) (twit/status-update) timing-pool))
