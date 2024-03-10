(ns compojure-tutorial.components.text-input
  (:require [clojure.spec.alpha :as s]))

(defn text-input
  "Props:
   - :id
   - :name
   - :value
   - :width-css"
  [props]
  (let [id (:id props)
        name (:name props)
        value (:value props)
        {width-css :width-css, :or {width-css "w-full"}} props
        class-str (str "py-1 px-2 outline-none animate-color duration-300 border-[1px] border-gray-300 focus:border-blue-300 rounded-[3px] " width-css)]
    [:input
     {:type "text"
      :id id
      :class class-str
      :name name
      :value value}]))
