(ns compojure-tutorial.components.loading-spinner
  (:require [compojure-tutorial.utils.twm :refer [twm]]))


(defn loading-spinner
  ;; Pass in :id and :class as props. Used with hx-indicator
  ([] (loading-spinner {:id "spinner"}))
  ([props]
   (let [class-str (twm "w-3" (:class props))]
     [:img.animate-spin
      {:id (:id props)
       :class class-str
       :src "/img/loading-spinner.svg"
       :alt "loading-spinner"}])))
