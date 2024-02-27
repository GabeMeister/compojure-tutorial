(ns compojure-tutorial.utils.env
  (:require [clojure.data.json :as json]))

(defn env
  []
  (json/read-str (slurp "./env.json")))

(defn get-env
  [key]
  (get (env) key))
