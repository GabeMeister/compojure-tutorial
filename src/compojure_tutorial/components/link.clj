(ns compojure-tutorial.components.link)

(defn link
  ([attr]
   (link attr nil))
  ([attr content]
   [:a
    (merge {:class "cursor-pointer text-blue-500 hover:text-blue-700 transition duration-500"} attr)
    content]))