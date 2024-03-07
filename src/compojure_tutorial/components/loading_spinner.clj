(ns compojure-tutorial.components.loading-spinner
  (:require [clojure.spec.alpha :as s]))

(s/def ::loading-spinner-props (s/keys :opt [::id]))

(def DEFAULTS {::id "spinner"})
(assert (s/valid? ::loading-spinner-props DEFAULTS))

(defn loading-spinner
  [props]
  (assert (s/valid? ::loading-spinner-props props))

  (let [merged-props (merge DEFAULTS props)]
    [:img.animate-spin
     {:id (get merged-props :id)
      :class "htmx-indicator w-3"
      :src "/img/loading-spinner.svg"
      :alt "loading-spinner"}]))