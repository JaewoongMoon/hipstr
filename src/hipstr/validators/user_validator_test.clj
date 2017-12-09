(ns hipstr.)

(deftest test-value-must-fall-between-1-and-9
  (let [some-val 10]
    (is (some #{some-val} (range 1 10)))))
