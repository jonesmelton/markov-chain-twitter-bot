(ns markov-twooter.core
  (:require [markov-twooter.twitter-interface :as twit :refer [status-update]])
  (:gen-class))

(defn -main
  "Public interface for markov-generator."
  [& args]
  (twit/status-update))
