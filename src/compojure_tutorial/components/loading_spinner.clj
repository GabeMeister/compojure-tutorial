(ns compojure-tutorial.components.loading-spinner)


(defn loading-spinner
  ([] (loading-spinner {:id "spinner"}))
  ([props]
   (let [{width-css :width-css, :or {width-css "w-3"}} props
         props (dissoc props :width-css)]
     [:img.animate-spin
      {:id (:id props)
       :class (str width-css " " (:class props))
       :src "/img/loading-spinner.svg"
       :alt "loading-spinner"}])))