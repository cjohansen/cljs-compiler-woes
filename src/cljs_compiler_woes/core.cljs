(ns cljs-compiler-woes.core
  (:require [cljs.nodejs :as nodejs]
            [medley.core :refer [map-vals]]
            [cljs-compiler-woes.time :as time]))

(nodejs/enable-util-print!)

(defn -main [& args]
  (println "The time is now" (time/human-month-year (js/Date.)))
  (println (map-vals keyword {:a "Hello" :b "World"})))

(set! *main-cli-fn* -main)
