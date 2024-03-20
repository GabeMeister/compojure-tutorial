(ns compojure-tutorial.utils.map)

(defn remove-nested
  [m [k & ks]]
  (if (empty? ks)
    (dissoc m k)
    (update-in m [k] remove-nested ks)))
