(ns markov-twooter.core
  (:gen-class))

(defn word-chain [word-transitions]
  (reduce (fn [ r t] (merge-with clojure.set/union r
                                 (let [[a b c] t]
                                   {[a b] (if c 
                                            #{c} 
                                            #{})})))
          {}
          word-transitions))

(defn text->word-chain [s]
  (let [words (clojure.string/split s #"[\s|\n]")
        word-transitions (partition-all 3 1 words)]
    (word-chain word-transitions)))

(defn walk-chain [prefix chain result]
  (let [suffixes (get chain prefix)]
    (if (empty? suffixes)
      result
      (let [suffix (first (shuffle suffixes))
            new-prefix [(last prefix) suffix]]
        (recur new-prefix chain (conj result suffix))))))

(defn -main
  "Public interface for markov-generator."
  [& args]
  (println "In Progress"))
