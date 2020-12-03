(ns aoc2020.day3
  (:require [aoc2020.utils :as utils]))

(def input
  (->> "day3"
       (utils/lines-from)))

(defn value-at [[x y]]
  (-> input
      (nth y)
      (cycle)
      (nth x)))

(defn count-trees [right down]
  (count
    (for [i (range (/ (count input) down))
          :let [y (* i down)
                x (* i right)
                val (value-at [x y])]
          :when (= val \#)]
      val)))

(println "Solution first part: " (count-trees 3 1))
(println "Solution second part: "
         (* (count-trees 1 1)
            (count-trees 3 1)
            (count-trees 5 1)
            (count-trees 7 1)
            (count-trees 1 2)))