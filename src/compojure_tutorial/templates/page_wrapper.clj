(ns compojure-tutorial.templates.page-wrapper)

(defn page-wrapper
  [content]
  [:html
   [:head
    [:script {:src "https://unpkg.com/htmx.org@1.9.10/dist/htmx.min.js"}]
    [:link {:rel "icon"
            :type "image/x-icon"
            :href "/img/favicon.ico"}]
    [:link {:rel "stylesheet"
            :href "/css/styles.css"}]
    [:title "Compojure Tutorial"]]
   [:body
    content]])