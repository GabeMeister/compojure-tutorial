(ns compojure-tutorial.utils.twm
  (:require [clojure.string :as str]
            [compojure-tutorial.utils.coll :refer [find-index]]))

;;
;; CONSTS
;;

(def CSS-PROPERTIES ["w",
                     "max-w",
                     "min-w",
                     "m",
                     "mt",
                     "ml",
                     "mr",
                     "mb",
                     "my",
                     "mx",
                     "p",
                     "pt",
                     "pl",
                     "pr",
                     "pb",
                     "py",
                     "px",
                     "text",
                     "bg",
                     "rounded",
                     "animate",
                     "duration",
                     "outline",
                     "border",
                     "cursor"])
(def CSS-PROPERTY-PREFIXES (map #(str % "-") CSS-PROPERTIES))

;;
;; FUNCTIONS
;;

(defn match-css-property
  [class]
  (let [index (find-index #(str/starts-with? class %) CSS-PROPERTY-PREFIXES)
        css-prop (get CSS-PROPERTIES index)]
    css-prop))

(defn css-map-to-coll
  "Given a map of css properties/modifiers to classes, 
   return the final css class string"
  [class-map]
  (println "TODO"))


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

(defn get-modifiers
  "Given a string css class (e.g. `md:hover:text-blue-100`),
   return a collection of the modifiers in alphabetical order
   (e.g. ['hover', 'md'])"
  [class]
  (if (str/includes? class ":")
    (let [modifiers (butlast (sort (str/split class #":")))]
      modifiers)
    '()))

(defn parse-css-property
  "Given a css class, return just the css property itself
   without modifiers (e.g. `md:hover:`) or spacing 
   (e.g. `-[20px]` or `-24`)"
  [class]
  ;; Example class: `md:hover:w-24`
  (let [class-without-modifier (last (str/split class #":"))
        class-without-spacing (first (str/split class-without-modifier #"-"))]
    class-without-spacing))

(defn css-reducer
  "Given a css map, add in the appropriate keys/value of the additional css class"
  [css-map class]
  (let [css-property (parse-css-property class)
        modifiers (get-modifiers class)
        map-path (conj modifiers css-property)]
    (assoc-in css-map map-path {"value" class})))

(defn css-coll-to-map
  "Given a collection of css classes, return a map with 
   root-level keys as css classes, nested keys as modifiers, 
   and values as the classes themselves"
  [css-coll]
  (reduce css-reducer {} css-coll))

(defn css-str-to-coll
  [css-str]
  (sort (str/split css-str #" ")))

(defn css-to-map
  "Given a css string of several css classes, return a map
   representing the css structure"
  [css-str]
  (let [css-coll (css-str-to-coll css-str)
        css-map (css-coll-to-map css-coll)]
    css-map))

(defn twm
  "Override default tailwind classes with new tailwind classes"
  [orig-css override-css]
  (let [orig-css-map (css-to-map orig-css)
        override-css-map (css-to-map override-css)]
    {"orig" orig-css-map, "override" override-css-map}))

(twm "w-4" "w-6 mb-8 hover:mb-10")
