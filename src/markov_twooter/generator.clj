(ns markov-twooter.generator
  (:require [clojure.set :refer [union]]))

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

(defn chain->text [chain]
 (clojure.string/join " " chain) )

(defn walk-chain [prefix chain result]
  (let [suffixes (get chain prefix)]
    (if (empty? suffixes)
      result
      (let [suffix (first (shuffle suffixes))
            new-prefix [(last prefix) suffix]
            result-with-spaces (chain->text result)
            result-char-count (count result-with-spaces)
            suffix-char-count (inc (count suffix))
            new-result-char-count (+ result-char-count suffix-char-count)]
        (if (>= new-result-char-count 140)
          result
          (recur new-prefix chain (conj result suffix)))))))

(defn generate-text
  [start-phrase word-chain]
  (let [prefix (clojure.string/split start-phrase #" ")
        result-chain (walk-chain prefix word-chain prefix)
        result-text (chain->text result-chain)]
    result-text))

(defn process-file [fname]
  (text->word-chain
   (slurp (clojure.java.io/resource fname))))

(def files ["ij.txt"])

(def processed-files (apply merge-with clojure.set/union (map process-file files)))

(def prefix-list ["But then" "And now" "Though the"
                       "Just why" "And but" "The Moms"
                       "There was" "The thing" "And it"
                       "The way" "it also" "can stand"
                       "more than" "More than" "against the"
                       "Against the" "to keep" "at the"])

(defn end-at-last-punctuation [text]
  (let [trimmed-to-last-punct (clojure.string/join  (re-seq #"[\s\w]+[^.!?,]*[.!?,]" text))
        trimmed-to-last-word (clojure.string/join (re-seq #".*[^a-zA-Z]+" text))
        result-text (if (empty? trimmed-to-last-punct)
                      trimmed-to-last-word
                      trimmed-to-last-punct)
        cleaned-text (clojure.string/replace result-text #"[,| ]$" ".")]
    (clojure.string/replace cleaned-text #"\"" "'")))

(defn tweet-text []
  (let [text (generate-text (-> prefix-list shuffle first) processed-files)]
    (end-at-last-punctuation text)))
