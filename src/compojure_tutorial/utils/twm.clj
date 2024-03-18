(ns compojure-tutorial.utils.twm
  (:require [clojure.string :as str]))

;;
;; CONSTS
;;

;; We might use these later

;; (def CSS-PROPERTIES ["w",
;;                      "max-w",
;;                      "min-w",
;;                      "m",
;;                      "mt",
;;                      "ml",
;;                      "mr",
;;                      "mb",
;;                      "my",
;;                      "mx",
;;                      "p",
;;                      "pt",
;;                      "pl",
;;                      "pr",
;;                      "pb",
;;                      "py",
;;                      "px",
;;                      "text",
;;                      "bg",
;;                      "rounded",
;;                      "animate",
;;                      "duration",
;;                      "outline",
;;                      "border",
;;                      "cursor"])

;; For testing purposes 

;; (def EXAMPLE-CSS-MAP {"w" {"value" "w-5"
;;                            "hover" {"value" "hover:w-10"}}
;;                       "m" {"value" "m-3"}
;;                       "p" {"value" "p-2"}})


;;
;; EXAMPLE DATA
;;

;; CSS INPUT: 'text-green-700 hover:text-yellow-500 md:hover:text-red-500'

;; CSS MAP:
;; {
;;   text: {
;;     value: 'text-green-700',
;;     hover: {
;;       value: 'text-yellow-500',
;;       md: {
;;         value: 'text-red-500',
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
  (println css-map)
  (str/trim
   (reduce (fn [acc css-str]
             (str acc " " (str/trim css-str)))
           ""
           (map (fn [key] (recursive-get-css (get css-map key) "")) (keys css-map)))))

(defn- get-modifiers
  ;; Given a string css class (e.g. `md:hover:text-blue-100`), return a
  ;; collection of the modifiers in alphabetical order (e.g. ['hover', 'md'])
  [class]
  (if (str/includes? class ":")
    (let [modifiers (butlast (sort (str/split class #":")))]
      modifiers)
    '()))

(defn- parse-css-property
  ;; Given a css class, return just the css property itself without modifiers
  ;; (e.g. remove `md:hover:` in front) and without spacing (e.g. `-[20px]` or
  ;; `-24` at the end)
  [class]
  ;; Example class: `md:hover:w-24`
  (let [class-without-modifier (last (str/split class #":"))
        class-without-spacing (first (str/split class-without-modifier #"-"))]
    class-without-spacing))

(defn- css-reducer
  ;; Given a css map, add in the appropriate keys and final value of the given
  ;; css class
  [css-map class]
  (let [css-property (parse-css-property class)
        modifiers (get-modifiers class)
        map-path (conj modifiers css-property)]
    (assoc-in css-map map-path {"value" class})))

(defn- css-coll-to-map
  ;; Given a collection of css classes, return a map with root-level keys as css
  ;; classes, nested keys as modifiers, and values as the classes themselves
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
