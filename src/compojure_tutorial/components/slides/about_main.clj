(ns compojure-tutorial.components.slides.about-main
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.db.repos :refer [get-repo-by-id]]))

(defn about-main-slide
  [id-num]
  (let [repo-map (get-repo-by-id id-num)]
    (templ
     [:div
      "This is the about main slide for " (:repos/name repo-map)
      [:br]
      [:a
       {:href (str "/presentation/" id-num "?slide=new_authors&part=title")}
       "Next"]])))
