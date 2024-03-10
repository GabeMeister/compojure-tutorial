(ns compojure-tutorial.components.link)

(defn link
  ([attr]
   (link attr nil))
  ([attr content]
   (let [class (:class attr)
         attr (dissoc attr :class)]
     [:a
      (merge {:class (str "cursor-pointer text-blue-500 hover:text-blue-700 transition duration-500 " class)}
             attr)
      content])))