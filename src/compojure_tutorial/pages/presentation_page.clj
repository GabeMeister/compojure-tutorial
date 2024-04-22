(ns compojure-tutorial.pages.presentation-page
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.db.repos :refer [get-repo-data-part]]
            [compojure-tutorial.components.slides.about-main :refer [about-main-slide]]
            [compojure-tutorial.components.slides.new-authors-title :refer [new-authors-title-slide]]
            [compojure-tutorial.components.slides.new-authors-prev-year :refer [new-authors-prev-year-slide]]
            [compojure-tutorial.components.slides.new-authors-curr-year :refer [new-authors-curr-year-slide]]
            [compojure-tutorial.components.slides.new-authors-names-list :refer [new-authors-names-list-slide]]
            [compojure-tutorial.components.slides.team-commits-title :refer [team-commits-title-slide]]
            [compojure-tutorial.components.slides.team-commits-prev-year :refer [team-commits-prev-year-slide]]
            [compojure-tutorial.components.slides.team-commits-curr-year :refer [team-commits-curr-year-slide]]
            [compojure-tutorial.utils.conversions :refer [str-to-int]]
            [clojure.data.json :as json]))

(defn presentation-page
  [id-str slide-str part-str]
  (let [slide-type-str (str slide-str "|" part-str)
        id-num (str-to-int id-str)]
    (case slide-type-str
      "|" (about-main-slide id-num)
      "about|main" (about-main-slide id-num)
      "new_authors|title" (new-authors-title-slide id-num)
      "new_authors|prev_year_number" (new-authors-prev-year-slide id-num)
      "new_authors|curr_year_number" (new-authors-curr-year-slide id-num)
      "new_authors|list_names" (new-authors-names-list-slide id-num)
      "team_commits|title" (team-commits-title-slide id-num)
      "team_commits|prev_year_number" (team-commits-prev-year-slide id-num)
      "team_commits|curr_year_number" (team-commits-curr-year-slide id-num)
      "Unrecognized slide")))
