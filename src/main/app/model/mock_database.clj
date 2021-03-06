(ns app.model.mock-database
  "This is a mock database implemented via Datascript, which runs completely in memory, has few deps, and requires
  less setup than Datomic itself.  Its API is very close to Datomics, and for a demo app makes it possible to have the
  *look* of a real back-end without having quite the amount of setup to understand for a beginner."
  (:require
    [datascript.core :as d]
    [mount.core :refer [defstate]]))

;; In datascript just about the only thing that needs schema
;; is lookup refs and entity refs.  You can just wing it on
;; everything else.
(def schema {:person/id       {:db/cardinality :db.cardinality/one
                               :db/unique      :db.unique/identity}
             :address/id      {:db/cardinality :db.cardinality/one
                               :db/unique      :db.unique/identity}
             :person/address  {:db/cardinality :db.cardinality/one
                               :db/valueType   :db.type/ref}
             :person/children {:db/cardinality :db.cardinality/many
                               :db/valueType   :db.type/ref}})

(defn new-database [] (d/create-conn schema))

(defstate conn :start
  (let [c (new-database)]
      ;; Seed some data
    (d/transact! c [{:db/id "1" :person/id 1 :person/address "6" :person/happy? true :person/email "amiee@nowhere.com"}
                    {:db/id "2" :person/id 2 :person/happy? true :person/children ["3"] :person/email "sam@nowhere.com"}
                    {:db/id "3" :person/id 3 :person/address "5" :person/happy? true :person/email "sally@nowhere.com"}
                    {:db/id "4" :person/id 4 :person/happy? true :person/children ["1" "2"] :person/email "grut@nowhere.com"}
                    {:db/id "5" :address/id 100 :address/street "123 Main St" :address/city "New York"}
                    {:db/id "6" :address/id 101 :address/street "999 Broadway" :address/city "Miami"}])
    c))
