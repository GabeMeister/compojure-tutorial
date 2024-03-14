(ns compojure-tutorial.twm-test
  (:require [clojure.test :refer :all]
            [compojure-tutorial.utils.twm :refer [twm]]))


(deftest test-twm
  (testing "tailwind merge"
    (is (= (twm "m-1" "m-2") "m-2"))))