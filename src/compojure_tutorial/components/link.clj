(ns compojure-tutorial.components.link)

(defn link
  ([props]
   (link props nil))
  ([props content]
   (let [class (:class props)
         attr (dissoc props :class)]
     [:a
      (merge {:class (str "cursor-pointer text-blue-500 hover:text-blue-700 transition duration-500 " class)}
             attr)
      content])))