(ns compojure-tutorial.db.repos
  (:require [next.jdbc :as jdbc]
            [compojure-tutorial.utils.env :refer [get-env]]
            [clojure.spec.alpha :as s]
            [clojure.data.json :as json]))

(def db-spec
  {:dbtype   "postgresql"
   :dbname   (get-env "DB_NAME")
   :host     (get-env "DB_HOST")
   :port     (get-env "DB_PORT")
   :user     (get-env "DB_USER")
   :password (get-env "DB_PASSWORD")})

(def datasource (jdbc/get-datasource db-spec))

(s/def :repos/id int?)
(s/def :repos/name string?)
(s/def ::repo (s/keys :req [:repos/id
                            :repos/name]))
(s/def ::repos (s/coll-of ::repo))

(defn get-all-repos
  []
  (let [repos (jdbc/execute! datasource
                             ["SELECT id, name FROM repos"])]

    (assert (s/valid? ::repos repos))
    repos))

(defn get-repo-by-id
  [id]
  (let [rows (jdbc/execute! datasource
                            ["SELECT id, name from repos where id = ?" id])
        final (first rows)]
    final))

(defn update-repo-name
  [id name]
  (let [result (jdbc/execute! datasource
                              ["UPDATE repos set name = ? where id = ?" (str name) id])]
    result))

(defn get-repo-data
  [id-str]
  (let [id (Integer/parseInt id-str)
        result (jdbc/execute-one! datasource
                                  ["select id, name, data from repos where id = ?" id])]
    {"id" id
     "name" (:repos/name result)
     "data" (json/read-str (str (:repos/data result)))}))

(defn get-repo-data-part
  [id-str part]
  (let [id (Integer/parseInt id-str)
        sql-str (str "select id, name, data->'" part "' as stats from repos where id = ?")
        result (jdbc/execute-one! datasource
                                  [sql-str id])]
    (json/read-str (str (:stats result)))))