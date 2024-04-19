(ns compojure-tutorial.components.slides.about-main
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.db.repos :refer [get-repo-by-id]]
            [compojure-tutorial.components.loading-spinner :refer [loading-spinner]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]))

(defn about-main-slide
  [id-num]
  (let [repo-map (get-repo-by-id id-num)
        next-slide-url-str (str "/presentation/" id-num "?slide=new_authors&part=title")]
    (templ (page-wrapper
            [:div
             [:div "This is the about main slide for " (:repos/name repo-map)]

             [:div {:class "next-btn-wrapper"}
              [:a {:hx-get next-slide-url-str
                   :hx-target "body"
                   :hx-swap "outerHTML"
                   :hx-push-url "true"
                   :hx-indicator "closest .next-btn-wrapper"
                   :class "original-content cursor-pointer"}
               "Next"]
              [:div {:class "h-6 w-6 child-loading-spinner"}
               (loading-spinner {:class "w-6 h-6 "})]]


                 ;; [:a {:href (str "/presentation/" id-num "?slide=new_authors&part=title")}
                 ;;  "Next"]
             ]))))
