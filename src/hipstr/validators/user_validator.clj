(ns hipstr.validators.user-validator
  (:require [validateur.validation :refer :all]
            [noir.validation :as v]
            ))

(def email-validator
  (validation-set
   (validate-with-predicate :email
                            #(v/is-email? (:email %))
                            :message-fn
                            (fn [validation-map]
                              (if (v/has-value? (:email validation-map))
                                "The email's format is incorrect"
                                "is a required field")))))

(def username-validator
  (validation-set
   (format-of :username
              :format #"^[a-zA-Z0-9_]*$"
              :blank-message "is a required field"
              :message "Only letters, numbers, and underscores allowed.")))

(def password-validator
  (validation-set
   (length-of :password
              :within (range 8 101)
              :blank-message "is a required field."
              :message-fn (fn [type m attribute & args]
                            (if (= type :blank)
                              "is a required field"
                              "Passwords must be between 8 and 100 characters long.")))))

(defn validate-signup [signup]
  "Validates the incoming signup map and returns a set of error messages for any invalid field."
  ((compose-sets email-validator username-validator password-validator)
   signup))

;; (defn validate-signup [signup]
;;   "Validates the incoming map of values from our signup form,
;; and returns a set of error messages for any invalid key.
;; Expects signup to have :username, :email, and :password."
;;   (let [v (validation-set 
;;            (presence-of #{:username :email :password}
;;                         :message "is a required field")
;;            (format-of :username
;;                       :format #"^[a-zA-Z0-9_]*$"
;;                       :message "Only letters, numbers, and underscores allowed."
;;                       :blank-message "is a required field")
;;            (length-of :password
;;                       :within (range 8 101)
;;                       :message-fn 
;;                       (fn [type m attribute & args]
;;                         (if (= type :blank)
;;                           "is a required field"
;;                           "Passwords must be between 8 and 100 characters long.")))
;;            (validate-with-predicate :email
;;                                     #(v/is-email? (:email %))
;;                                     :message-fn
;;                                     (fn [validation-map]
;;                                       (if (v/has-value? (:email validation-map))
;;                                         "The email's format is incorrect"
;;                                         "is a required field")))
;;            )]   
;;     (v signup)))






