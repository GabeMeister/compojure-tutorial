(ns compojure-tutorial.components.loading-spinner
  (:require [compojure-tutorial.utils.twm :refer [twm]]))


(defn loading-spinner
  ([] (loading-spinner {:id "spinner"}))
  ([props]
   (let [class-str (twm "w-3" (:class props))
         props (dissoc props :width-css)]
     [:img.animate-spin
      {:id (:id props)
       :class class-str
       :src "/img/loading-spinner.svg"
       :alt "loading-spinner"}])))