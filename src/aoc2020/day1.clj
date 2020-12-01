(ns aoc2020.day1
  (:require [aoc2020.utils :as utils]))

(def input
  (->> "day1"
       (utils/lines-from)
       (map utils/string->int)))

(def first-part (for [n1 input
                     n2 input
                     :let [sum (+ n1 n2)]
                     :when (= sum 2020)]
                  (* n1 n2)))

(println "Solution first part: " (first first-part))

(def second-part (for [n1 input
                       n2 input
                       n3 input
                       :let [sum (+ n1 n2 n3)]
                       :when (= sum 2020)]
                   (* n1 n2 n3)))

(println "Solution second part: " (first second-part))