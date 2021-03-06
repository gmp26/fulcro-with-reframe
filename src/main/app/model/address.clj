(ns app.model.address
  (:require
    [com.wsscode.pathom.connect :as pc :refer [defresolver #_defmutation]]
    ;[taoensso.timbre :as log]
    [datascript.core :as d]))

(defresolver address-resolver [{:keys [db] :as _env} {:address/keys [id]}]
  {::pc/input  #{:address/id}
   ::pc/output [:address/id :address/street :address/city]}
  (d/pull db [:address/id :address/street :address/city] [:address/id id]))

(def resolvers [address-resolver])
