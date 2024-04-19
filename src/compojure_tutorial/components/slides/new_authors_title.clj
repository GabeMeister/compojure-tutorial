(ns compojure-tutorial.components.slides.new-authors-title
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.db.repos :refer [get-repo-by-id]]
            [compojure-tutorial.components.loading-spinner :refer [loading-spinner]]))

(defn new-authors-title-slide
  [id-num]
  (let [repo-map (get-repo-by-id id-num)
        prev-slide-url-str (str "/presentation/" id-num "?slide=about&part=main")]
    (templ
     [:div
      "This is the new authors title slide for " (:repos/name repo-map)
      [:br]

      [:div {:class "next-btn-wrapper"}
       [:a {:hx-get prev-slide-url-str
            :hx-target "body"
            :hx-swap "outerHTML"
            :hx-push-url "true"
            :hx-indicator "closest .next-btn-wrapper"
            :class "original-content cursor-pointer"}
        "Prev"]
       [:div {:class "h-6 w-6 child-loading-spinner"}
        (loading-spinner {:class "w-6 h-6 "})]]])))
