(ns cljs-compiler-woes.time
  (:require cljsjs.moment
            cljsjs.moment.locale.nb))

(.locale js/moment "nb")

(defn human-month-year [date]
  (.format (js/moment date) "MMMM YYYY"))
