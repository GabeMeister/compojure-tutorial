(ns compojure-tutorial.utils.twm
  (:require [clojure.string :as str]
            [compojure-tutorial.utils.map :refer [remove-nested]]))

;;
;; CONSTS
;;

(def RELATED-CSS-PROPERTIES {"p" ["pt" "pr" "pb" "pl"]
                             "m" ["mt" "mr" "mb" "ml"]})

;;
;; EXAMPLES
;;

;; GIVEN CSS INPUT: 'text-green-700 hover:text-yellow-500 md:hover:text-red-500'
;; CORRESPONDING CSS MAP:
;; {
;;   "text" {
;;     "value" "text-green-700"
;;     "hover" {
;;       "value" "text-yellow-500"
;;       "md" {
;;         "value" "text-red-500"
;;       }
;;     }
;;   }
;; }

;;
;; FUNCTIONS
;;

(defn- recursive-get-css
  ;; Given a map of css, iterate through all the keys/values of the map and form
  ;; a string containing all of the css classes
  [m css-str]
  (reduce (fn [acc [k, v]] (if (= k "value")
                             (str/trim (str acc " " v))
                             (str/trim (str acc " " (recursive-get-css v css-str)))))
          css-str
          m))

(defn- css-map-to-str
  ;; Given a map of css properties/modifiers to classes, return the final css
  ;; class string
  [css-map]
  (str/trim (reduce (fn [acc css-str] (str acc " " (str/trim css-str)))
                    ""
                    (map (fn [key] (recursive-get-css (get css-map key) "")) (keys css-map)))))

(defn- parse-modifiers
  ;; Given a string css class (e.g. `md:hover:text-blue-100`), return a list of
  ;; the modifiers in alphabetical order ( e.g. '('hover', 'md') )
  [class]
  (if (str/includes? class ":")
    (let [modifiers (butlast (sort (str/split class #":")))]
      modifiers)
    '()))

(defn- parse-css-property
  ;; Given a full css class, return just the css property itself without modifiers
  ;; (e.g. remove `md:hover:` in front) and without spacing (e.g. `-[20px]` or
  ;; `-24` at the end)
  [class]
  ;; Example class: `md:hover:w-24`
  (let [class-without-modifier (last (str/split class #":"))
        class-without-spacing (first (str/split class-without-modifier #"-"))]
    class-without-spacing))

(defn- remove-css-reducer
  ;; Given a css map, remove the given "path" of keys from the map. The first
  ;; item in the path should be a css class property, and the rest are
  ;; modifiers, ending with "value"
  [css-map path-to-remove]
  (if (contains? css-map (first path-to-remove))
    (remove-nested css-map path-to-remove)
    css-map))

(defn- get-css-paths
  ;; Given a css path as a vec, return a list of additional css paths for
  ;; related css properties
  [css-path related-css-properties]
  (map (fn [css-prop] (assoc css-path 0 css-prop)) related-css-properties))

(defn- remove-related-css-properties
  ;; Given a css map and a css path (for example, ["mt", "hover", "md"]), remove
  ;; css key/value that get "stomped". For example, if the original css was
  ;; `pt-5`, but we want to override with `p-2`, then we need to remove the
  ;; keys/value of `pt-5` (even though they are different css class properties)
  [css-map path]
  (let [css-property (first path)]
    (if (contains? RELATED-CSS-PROPERTIES css-property)
      (let [css-paths-to-remove (get-css-paths path
                                               (get RELATED-CSS-PROPERTIES css-property))]
        (reduce remove-css-reducer css-map css-paths-to-remove))
      css-map)))

(defn- css-reducer
  ;; Given a css map, add in the appropriate keys and final value of the given
  ;; css class, and also remove any keys/values of css properties that get
  ;; "stomped" by the newly added css class
  [css-map class]
  (let [css-property (parse-css-property class)
        l-modifiers (parse-modifiers class)
        css-path (conj l-modifiers css-property)
        css-path-with-value (conj (vec css-path) "value")
        css-map-with-new-css-class (assoc-in css-map css-path {"value" class})
        css-map-with-classes-filtered-out (remove-related-css-properties css-map-with-new-css-class
                                                                         css-path-with-value)]
    css-map-with-classes-filtered-out))

(defn- css-coll-to-map
  ;; Given a collection of css classes, return a map with root-level keys as css
  ;; class properties (e.g. `pt` for padding top), nested keys as modifiers
  ;; (e.g. `md` or `hover`), and the values as the full css classes themselves
  ;; (e.g. `hover:pt-4`)
  ([css-coll] (css-coll-to-map css-coll {}))
  ([css-coll init-map]
   (reduce css-reducer init-map css-coll)))

(defn- css-str-to-coll
  ;; Given a string of css classes, return a collection of each individual class
  [css-str]
  (sort (str/split css-str #" ")))

(defn- css-to-map
  ;; Given a css string of several css classes, return a map representing the
  ;; css structure
  ([css-str] (css-to-map css-str {}))
  ([css-str init-map]
   (let [css-coll (css-str-to-coll css-str)
         css-map (css-coll-to-map css-coll init-map)]
     css-map)))

(defn twm
  ;; Override default tailwind classes with new tailwind classes
  [orig-css override-css]
  (if (or (nil? override-css)
          (= "" override-css))
    orig-css
    (let [orig-css-map (css-to-map orig-css)
          override-css-map (css-to-map override-css orig-css-map)
          new-css (css-map-to-str override-css-map)]
      new-css)))


(twm "mt-1" "mt-2")    ;;  ->  "mt-2"
(twm "md:mt-1" "mt-2")   ;;  ->  "md:mt-1 mt-2" INCORRECT
(twm "md:mt-1" "md:mt-2")   ;;  ->  "md:mt-2"
(twm "md:mt-1 random-class-gabe" "md:mt-2")   ;;  ->  "random-class-gabe md:mt-2"
(twm "mt-1 md:mt-4" "mt-2")   ;;  ->  "md:mt-4 mt-2" INCORRECT
(twm "mt-1 md:mt-4" "mt-2 md:mt-6")   ;;  ->  "mt-2 md:mt-6" INCORRECT
(twm "mt-1 md:mt-4" "mt-2 md:mt-6 lg:mt-10")   ;;  ->  "mt-2 md:mt-6 lg:mt-10" INCORRECT
(twm "mt-1" "m-4")   ;;  ->  "m-4"
(twm "m-1" "mb-4")   ;;  ->  "m-1 mb-4"
(twm "mt-1" "mb-4")   ;;  ->  "mt-1 mb-4"