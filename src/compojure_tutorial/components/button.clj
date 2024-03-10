(ns compojure-tutorial.components.button)

(defn- color-to-css
  ([] (color-to-css "blue"))
  ([color]
   (case color
     "blue" "bg-blue-500 hover:bg-blue-700"
     "gray" "bg-gray-400 hover:bg-gray-600"
     "red" "bg-red-500 hover:bg-red-700"
     nil (color-to-css "blue"))))

(defn button
  "Props:
   - :color - the color you want your button to be"
  [attr content]
  (let [color (color-to-css (:color attr))
        class-str (str
                   "text-white px-3 py-1 rounded-[3px] animate-color duration-300 "
                   color
                   " "
                   (:class attr))
        btn-attr (merge attr {:class class-str})]
    [:button
     btn-attr
     content]))
