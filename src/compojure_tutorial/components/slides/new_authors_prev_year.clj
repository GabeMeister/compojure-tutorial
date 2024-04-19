(ns compojure-tutorial.components.slides.new-authors-prev-year
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.db.repos :refer [get-repo-by-id]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.components.link :refer [link]]
            [compojure-tutorial.components.loading-spinner :refer [loading-spinner]]))

(defn new-authors-prev-year-slide
  [id-num]
  (let [repo-map (get-repo-by-id id-num)
        prev-slide-url-str (str "/presentation/" id-num "?slide=new_authors&part=title")
        next-slide-url-str (str "/presentation/" id-num "?slide=new_authors&part=curr_year_number")]
    (templ
     (page-wrapper [:div {:class "flex flex-col items-center p-6"}
                    [:div "This is the new authors PREV YEAR slide for " (:repos/name repo-map)]

                    [:div {:class "flex"}
                     [:div {:class "w-12 flex justify-center prev-btn-wrapper"}
                      (link {:hx-get prev-slide-url-str
                             :hx-target "body"
                             :hx-swap "outerHTML"
                             :hx-push-url "true"
                             :hx-indicator "closest .prev-btn-wrapper"
                             :class "original-content cursor-pointer"}
                            "Prev")
                      [:div {:class "h-6 w-6 child-loading-spinner"}
                       (loading-spinner {:class "w-6 h-6 "})]]
                     [:div {:class "w-12 flex justify-center next-btn-wrapper ml-2"}
                      (link {:hx-get next-slide-url-str
                             :hx-target "body"
                             :hx-swap "outerHTML"
                             :hx-push-url "true"
                             :hx-indicator "closest .next-btn-wrapper"
                             :class "original-content cursor-pointer"}
                            "Next")
                      [:div {:class "h-6 w-6 child-loading-spinner"}
                       (loading-spinner {:class "w-6 h-6 "})]]]]))))