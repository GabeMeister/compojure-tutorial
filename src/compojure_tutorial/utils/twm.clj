(ns compojure-tutorial.utils.twm
  (:require [clojure.string :as str]
            [clojure.pprint :refer [pprint]]
            [compojure-tutorial.utils.map :refer [remove-nested]]
            [compojure-tutorial.data.twm-consts :refer [CLASS-MAP
                                                        CLASS-GROUPS
                                                        CONFLICTING-CLASSES]]))

;;
;; EXAMPLES
;;

;; Given the following css string:

;; "gabe-special-class inline-flex text-green-700 hover:text-yellow-500
;; md:hover:text-red-500"

;; The corresponding map is built below. Notice the root level keys are class
;; groups, modifiers are nested keys and finally the values are the css classes
;; themselves. Any classes that aren't ready to be handled are tossed into the
;; "unrecognized-classes" vector.

;; {"display" {"value" "inline-flex"}
;;  "text-color" {"value" "text-green-700"
;;                "hover" {"value" "text-yellow-500"
;;                         "md" "text-red-500"}}
;;  "unrecognized" ["gabe-special-class"]}

;;
;; FUNCTIONS
;;


(defn- recursive-get-css
  ;; Given a map of css and the current css string, iterate through all the
  ;; keys/values of the map and form a string containing all of the css classes
  [css-map css-str]
  (reduce (fn [final-css-str [key, val]] (if (= key "value")
                                           (str/trim (str final-css-str " " val))
                                           (str/trim (str final-css-str " " (recursive-get-css val css-str)))))
          css-str
          css-map))

(defn- css-class-group-map-to-str
  [[class-group-str css-map]]
  ;; the `unrecognized` top-level key is special because it is a list  of
  ;; unrecognized classes instead of a css map
  (if (= "unrecognized" class-group-str)
    (str/join " " css-map)
    (recursive-get-css css-map "")))

(defn- css-map-to-str
  ;; Given a css map, return the final css class string
  [css-map]
  (str/trim (reduce (fn [acc css-str] (str acc " " (str/trim css-str)))
                    ""
                    (map css-class-group-map-to-str css-map))))

(defn- parse-modifiers
  ;; Given a string css class (e.g. `md:hover:text-blue-100`), return a list of
  ;; the modifiers in alphabetical order ( e.g. '('hover', 'md') )
  [class]
  (if (str/includes? class ":")
    (let [modifiers (butlast (sort (str/split class #":")))]
      modifiers)
    '()))

(defn- is-recognized-class?
  [class-str]
  (contains? CLASS-MAP class-str))

(defn- parse-css-property
  ;; Given a full css class, return just the css property itself without modifiers
  ;; (e.g. remove `md:hover:` in front) and without spacing (e.g. `-[20px]` or
  ;; `-24` at the end)
  [class-str]
  ;; Example classes: `md:hover:w-24`, `flex`, `table-cell`
  (let [class-without-modifiers-str (last (str/split class-str #":"))]
    class-without-modifiers-str))

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

;; (defn- remove-related-css-properties
;;   ;; Given a css map and a css path (for example, ["mt", "hover", "md"]), remove
;;   ;; css key/value that get "stomped". For example, if the original css was
;;   ;; `pt-5`, but we want to override with `p-2`, then we need to remove the
;;   ;; keys/value of `pt-5` (even though they are different css class properties)
;;   [css-map path]
;;   (let [css-property (first path)]
;;     (if (contains? RELATED-CSS-PROPERTIES css-property)
;;       (let [css-paths-to-remove (get-css-paths path
;;                                                (get RELATED-CSS-PROPERTIES css-property))]
;;         (reduce remove-css-reducer css-map css-paths-to-remove))
;;       css-map)))

;; (defn- css-reducer
;;   ;; Given a css map, add in the appropriate keys and final value of the given
;;   ;; css class, and also remove any keys/values of css properties that get
;;   ;; "stomped" by the newly added css class
;;   [css-map class]
;;   (let [css-property (parse-css-property class)
;;         l-modifiers (parse-modifiers class)
;;         css-path (conj l-modifiers css-property)
;;         css-path-with-value (conj (vec css-path) "value")
;;         css-map-with-new-css-class (assoc-in css-map css-path (merge (get-in css-map css-path) {"value" class}))
;;         css-map-with-classes-filtered-out (remove-related-css-properties css-map-with-new-css-class
;;                                                                          css-path-with-value)]
;;     css-map-with-classes-filtered-out))

(defn- css-coll-to-map-reducer
  [css-map class-str]
  (let [css-property (parse-css-property class-str)]
    (if (is-recognized-class? css-property)
      (let [modifiers-list (parse-modifiers class-str)
            class-group (get CLASS-MAP css-property)
            css-path-list (conj (vec (conj modifiers-list class-group)) "value")]
        (assoc-in css-map css-path-list class-str))
      (assoc-in css-map ["unrecognized"] (conj (get css-map "unrecognized" []) class-str)))))

(defn- css-coll-to-map
  ;; Given a collection of css classes, return a map with root-level keys as css
  ;; class properties (e.g. `pt` for padding top), nested keys as modifiers
  ;; (e.g. `md` or `hover`), and the values as the full css classes themselves
  ;; (e.g. `hover:pt-4`)
  ([css-coll] (css-coll-to-map css-coll {}))
  ([css-coll init-map]
   (reduce css-coll-to-map-reducer init-map css-coll)))

(defn- css-str-to-list
  ;; Given a string of css classes, return a list of each individual class
  [css-str]
  (sort (str/split css-str #" ")))

(defn- css-to-map
  ;; Given a css string of several css classes, return a map representing the
  ;; css structure
  ([css-str] (css-to-map css-str {}))
  ([css-str init-map]
   (let [css-list (css-str-to-list css-str)
         css-map (css-coll-to-map css-list init-map)]
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

;; (twm "hover:md:text-blue-500 md:text-red-200 grid gabe-special-class" "text-sm flex md:text-yellow-600")
(twm "mt-5" "m-10")