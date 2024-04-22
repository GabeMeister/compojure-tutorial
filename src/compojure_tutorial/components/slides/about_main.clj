(ns compojure-tutorial.components.slides.about-main
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.db.repos :refer [get-repo-by-id]]
            [compojure-tutorial.utils.slides :refer [get-next-slide-info]]
            [compojure-tutorial.components.button :refer [button]]
            [compojure-tutorial.components.link :refer [link]]
            [compojure-tutorial.components.loading-spinner :refer [loading-spinner]]
            [compojure-tutorial.utils.url :refer [get-url-with-query-params]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]))


(defn about-main-slide
  [id-num]
  (let [repo-map (get-repo-by-id id-num)
        next-slide-url-str (get-url-with-query-params (format "/presentation/%s" id-num)
                                                      (get-next-slide-info "about" "main"))]
    (templ (page-wrapper
            [:div {:class "flex flex-col items-center justify-center min-h-screen p-6 bg-gray-800"}
             [:div [:h1 {:class "text-white text-7xl"} (:repos/name repo-map)]]

             [:div {:class "w-24 mt-6 flex justify-center next-btn-wrapper"}
              (button {:hx-get next-slide-url-str
                       :hx-target "body"
                       :hx-swap "outerHTML"
                       :hx-push-url "true"
                       :hx-indicator "closest .next-btn-wrapper"
                       :class "original-content cursor-pointer"}
                      "Next →")
              [:div {:class "h-6 w-6 child-loading-spinner"}
               (loading-spinner {:class "w-6 h-6 "})]]]))))
