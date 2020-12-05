(ns aoc2020.day5
  (:require [aoc2020.utils :as utils]))

(def input (->> "day5" (utils/lines-from)))

(defn interpret [instruction start end]
  (let [middle (int (/ (- end start) 2))]
    (if (contains? #{\F \L} instruction)
      [start (+ start middle)]
      [(+ start (inc middle)) end])))

(defn row-of [line]
  (reduce
    (fn [[start end] curr]
      (interpret curr start end))
    [0 127]
    (take 7 line)))

(defn column-of [line]
  (reduce
    (fn [[start end] curr]
      (interpret curr start end))
    [0 7]
    (drop 7 line)))

(defn seat-id-of [line]
  (+
    (* 8 (first (row-of line)))
    (first (column-of line))))

(println "Solution first part: "
         (->> (map seat-id-of input)
              (apply max)))

(println "Solution second part: "
         (->> (map seat-id-of input)
              (sort)
              (reduce
                (fn [acc curr]
                  (cond
                    (nil? acc) curr
                    (= (inc acc) curr) curr
                    :else (reduced (dec curr)))))))