(ns compojure-tutorial.db.repos
  (:require [next.jdbc :as jdbc]
            [compojure-tutorial.utils.env :refer [get-env]]))

(def db-spec
  {:dbtype   "postgresql"
   :dbname   (get-env "DB_NAME")
   :host     (get-env "DB_HOST")
   :port     (get-env "DB_PORT")
   :user     (get-env "DB_USER")
   :password (get-env "DB_PASSWORD")})

(def datasource (jdbc/get-datasource db-spec))

(defn get-all-repos
  []
  (let [raw-data (jdbc/execute! datasource
                                ["SELECT id, name FROM repos"])]
    (map (fn [row] {:id (:repos/id row)
                    :name (:repos/name row)})
         raw-data)))