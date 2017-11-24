(ns hipstr.validators.user-validator
  (:require [validateur.validation :refer :all]
            [noir.validation :as v]
            ))

(defn validate-signup [signup]
  "Validates the incoming map of values from our signup form,
and returns a set of error messages for any invalid key.
Expects signup to have :username, :email, and :password."
  (let [v (validation-set 
           (presence-of #{:username :email :password}
                        :message "is a required field")
           (format-of :username
                      :format #"^[a-zA-Z0-9_]*$"
                      :message "Only letters, numbers, and underscores allowed."
                      :blank-message "is a required field")
           (length-of :password
                      :within (range 8 101)
                      :message-fn 
                      (fn [type m attribute & args]
                        (if (= type :blank)
                          "is a required field"
                          "Passwords must be between 8 and 100 characters long.")))
           (validate-with-predicate :email
                                    #(v/is-email? (:email %))
                                    :message-fn
                                    (fn [validation-map]
                                      (if (v/has-value? (:email validation-map))
                                        "The email's format is incorrect"
                                        "is a required field")))
           )]   
    (v signup)))

; In the preceding code, we created our own little validate-signup function, which will take the parameters map from our POST/signup up route. At #1, we construct our validator by calling validator-set, and we pass it to our first rule, presence-of, which itself accepts the set of keys that must be present, and truthy, it the map to be deemed valid(in our case, the sign-up map).




