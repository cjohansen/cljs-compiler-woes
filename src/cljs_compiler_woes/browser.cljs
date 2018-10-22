(ns cljs-compiler-woes.browser
  (:require [cljs-compiler-woes.time :as time]
            [medley.core :refer [map-vals]]))

(println "The time is now" (time/human-month-year (js/Date.)))
(println (map-vals keyword {:a "Hello" :b "World"}))
