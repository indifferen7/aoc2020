(ns aoc2020.day9
  (:require [aoc2020.utils :as utils]))

(def input
  (->> "day9"
       (utils/lines-from)
       (map #(Long/parseLong %))))

(defn sum-combinations [ns]
  (for [idx-n1 (range (count ns))
        idx-n2 (range (count ns))
        :when (not (= idx-n1 idx-n2))]
    (+ (nth ns idx-n1) (nth ns idx-n2))))

(println "Solution first part:"
         (loop [numbers input]
           (let [preamble (take 25 numbers)
                 preamble-sums (set (sum-combinations preamble))
                 next (nth numbers 25)]
             (if (contains? preamble-sums next)
               (recur (drop 1 numbers))
               next))))

(println "Solution second part:"
         (first
           (for [idx-n1 (range (count input))
                 idx-n2 (range (inc idx-n1) (count input))
                 :let [contiguous-set (->> input (take (inc idx-n2)) (drop idx-n1))
                       sum (apply + contiguous-set)]
                 :when (= 1930745883 sum)]
             (+ (apply min contiguous-set) (apply max contiguous-set)))))