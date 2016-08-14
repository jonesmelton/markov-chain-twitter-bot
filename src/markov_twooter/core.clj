(ns markov-twooter.core
  (:require [markov-twooter.generator :as gen :refer [tweet-text]])
  (:gen-class))

(defn -main
  "Public interface for markov-generator."
  [& args]
  (println (gen/tweet-text)))
