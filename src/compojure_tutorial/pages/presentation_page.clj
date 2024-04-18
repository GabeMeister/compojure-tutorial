(ns compojure-tutorial.pages.presentation-page
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.db.repos :refer [get-repo-data-part]]
            [compojure-tutorial.components.slides.about-main :refer [about-main-slide]]
            [compojure-tutorial.components.slides.new-authors-title :refer [new-authors-title-slide]]
            [compojure-tutorial.utils.conversions :refer [str-to-int]]
            [clojure.data.json :as json]))

(defn presentation-page
  [id-str slide-str part-str]
  (let [slide-type-str (str slide-str "_" part-str)
        id-num (str-to-int id-str)]
    (case slide-type-str
      "about_main" (about-main-slide id-num)
      "new_authors_title" (new-authors-title-slide id-num)
      "Unrecognized slide")))

;; (let [repo-data {:dog 42}]
;;   (templ
;;    (page-wrapper [:div
;;                   {:class "p-6"}
;;                   [:pre id-str]
;;                   [:pre slide-type-str]
;;                   [:pre part-str]
;;                   [:pre (json/write-str repo-data :indent 2)]])))
