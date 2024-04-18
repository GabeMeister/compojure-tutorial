(ns compojure-tutorial.components.slides.new-authors-title
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.db.repos :refer [get-repo-by-id]]))

(defn new-authors-title-slide
  [id-num]
  (let [repo-map (get-repo-by-id id-num)]
    (templ
     [:div
      "This is the new authors title slide for " (:repos/name repo-map)
      [:br]
      [:a
       {:href (str "/presentation/" id-num "?slide=about&part=main")}
       "Prev"]])))
