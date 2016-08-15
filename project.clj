(defproject markov-twooter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                           [twitter-api "0.7.8"]
                           [environ "1.1.0"]
                           [clj-http "2.1.0"]
                           [overtone/at-at "1.2.0"]]
  :main ^:skip-aot markov-twooter.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-environ "1.1.0"]])
