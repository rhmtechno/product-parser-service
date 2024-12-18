create sequence locale_message_id_seq;


create table locale_message (
                                         id bigint primary key not null default nextval('locale_message_id_seq'::regclass),
                                         content text,
                                         locale character varying(255),
                                         version character varying(255)
);


alter sequence locale_message_id_seq owned by locale_message.id;



INSERT INTO public.locale_message (id, content, locale, version) VALUES (default, e'{
  "operation.success": "Operation is successful.",
  "record.not.found": "Record is not found.",
  "service.unavailable": "Requested service is unavailable.",
  "invalid.request.data": "Invalid request data.",
  "invalid.transaction.pin": "Your provided PIN must be numeric [0 - 9] and between four and six characters long.",
  "feature.wise.unique.tfa": "Same feature can not have multiple TFA type.",
  "tfa.config.not.found": "Two-Factor authentication configuration not found.",
  "otp.generate.failed": "Failed to generate OTP.",
  "bad.otp.attempt.limit": "You reached the consecutive OTP attempt limit. Please try again later.",
  "tfa.not.allowed": "This feature does not support two-factor authentication.",
  "tfa.session.already.used": "Please generate OTP again.",
  "generate.otp.again": "Please generate OTP again.",
  "otp.expired.exception": "Your OTP has been expired. Please generate OTP again.",
  "tfa.not.active": "Your TFA is not active.",
  "incorrect.otp.exception": "The submitted One-Time Password (OTP) is not valid. Please enter a valid OTP.",
  "incorrect.pin.exception": "Incorrect PIN",
  "pin.not.set.exception": "PIN is not set, Please first set your TFA PIN.",
  "tfa.type.not.eligible": "TFA type is not eligible.",
  "otp.lock.exception": "For one hour, your OTP is locked. Please try again when the time limit has expired.",
  "bad.otp.attempt.limit.for.non.registered.user": "You reached the consecutive OTP attempt limit. Please try again the next day.",
  "invalid.user.type": "User type is unknown.",
  "try.later.with.time": "Please try after %s minutes %s seconds.",
  "tfa.feature.not.support": "This feature is not supported TFA.",
  "tfa.type.not.support": "Unknown TFA type.",
  "not.eligible.for.otp.generation": "This feature is not eligible for generating OTP.",
  "inter.service.communication.exception": "Inter service communication exception.",
  "internal.service.exception": "Internal service exception.",
  "database.exception": "The system is unable to execute the request.",
  "record.already.exists": "Record already exists.",
  "fav.cus.limit.exceeded": "Favorite wallet number limit exceeded.",
  "tfa.settings.not.found": "No TFA settings found for this user",
  "tfa.failure.exception": "TFA Failed",
  "invalid.transaction.category.code": "Transaction category code is invalid",
  "invalid.transaction.feature.code": "Transaction category code is invalid",
  "invalid.phone.number": "Invalid Phone Number",
  "invalid.email.address": "Invalid Email Address",
  "invalid.password.pattern": "Invalid Password Pattern",
  "not.authorized": "Not Authorized",
  "limit.verification.failed": "Limit verification failed!",
  "cool.down.checking.failed": "Cool down checking failed!",
  "out.of.balance": "Requested amount is more than current balance",
  "bulk.batch.does.not.exist": "Bulk Batch does not exist",
  "merchant.hierarchy.does.not.exist": "Merchant Hierarchy does not exist for this user",
  "user.not.permitted": "User is not permitted for this action",
  "data.validation.failed": "Data validation failed",
  "incorrect.current.password": "Incorrect PIN",
  "nid.already.taken": "Nid is already taken",
  "user.not.found": "No user found",
  "invalid.wallet.account": "Wallet number is invalid.",
  "incorrect.credentials": "The submitted PIN is not valid. Please enter a valid PIN.",
  "otp.not.verified": "Otp is not valid. Please Enter valid otp",
  "checkout.session.expired": "Checkout time has been expired",
  "live.selfie.does.not.match": "Please make sure same person is completing whole EKYC process",
  "live.selfie.does.not.match.nid": "Live selfie does not match with Nid person image",
  "live.selfie.validation.required": "Please validate live selfie validation",
  "already.ekyc.approved": "EKYC already approved",
  "record.already.exist": "Record Already Exist",
  "wrong.information": "Wrong Information",
  "pin.not.set": "Pin is not set",
  "account.balance.not.found": "Account balance is not found",
  "forgot.password": "Please contact customer care for generating new temporary password",
  "pin.change.exception": "Pin can not change",
  "previous.password.exception": "Previous password cant not be use as new password",
  "birth.year.password.exception": "Birth year can not be use as new password",
  "user.identity.password.exception": "You can not use your mobile number as new password",
  "incorrect.password.pattern": "Password can not be consecutive digit",
  "invalid.password.length": "Pin should be 4 digits",
  "old.pin.not.allowed": "New and Old Pin is Same",
  "pin.contains.phone.number": "Pin contains phone number sequence",
  "pin.sequence.not.allowed": "Consecutive sequence is not allowed",
  "consecutive.number.not.allowed": "Consecutive numbers not allowed",
  "pin.contains.birth.year": "Birth year as Pin not allowed",
  "incorrect.current.pin": "Incorrect PIN",
  "invalid.narration.length": "Remarks length can not be more than 100 characters",
  "merchant.account.not.found": "No Registered Account is Found for the following number",
  "ekyc.approval.required": "Receiver EKYC is not completed yet. You can not do transaction with this receiver account",
  "phone.no.already.used": "Phone no already used",
  "not.wallet.user": "This mobile number is not registered in Pocket.",
  "human.verification.failed": "Please make sure you are human",
  "account.temporary.locked": "Account is temporarily locked",
  "invalid.bulk.portal.role": "Invalid Bulk Portal Role.",
  "merchant.not.found": "Merchant Not Found",
  "qr.parse.failed": " Failed to Scan QR Code",
  "profile.update.operation.success": "Profile is updated successfully!",
  "enable.biometric.login.operation.success": "Biometric has been enabled",
  "disable.biometric.login.operation.success": "Biometric has been disabled",
  "change.pin.operation.success": "PIN has been changed successfully!",
  "language.change.operation.success": "Language has been changed successfully!",
  "request.money.operation.success": "Money Request is successful!",
  "cancel.request.money.operation.success": "Money Request is cancelled",
  "contains.non.registered.accounts": "This file contains non-registered customer accounts",
  "contains.negative.amount": "This file contains negative amount",
  "amount.format.error": "Amount field format need to be General or Text or Number",
  "to.account.format.error": "To Account field format need to be General or Text",
  "remarks.format.error": "Remarks field format need to be General or Text",
  "invalid.notification.option": "Invalid notification option",
  "invalid.file.format.error": "Invalid file format",
  "file.already.exists": "This file name is already exists",
  "amount.exceeds.2.decimal.places": "Amount can not have more than 2 decimal places",
  "insufficient.balance": "Insufficient balance",
  "invalid.file.naming.format": "Invalid File Naming Format",
  "withdraw.failed": "Withdraw Failed",
  "cbs.client.id.already.used": "Cbs client ID already exist",
  "negative.value.not.accepted": "Negative value not accepted",
  "successfully.requested.to.checker": "Successfully requested to checker",
  "already.request.pending": "A Request is Already Pending",
  "no.hierarchy.level.set": "No Hierarchy Level Added",
  "new.password.and.confirm.password.not.same": "New password and confirm password is not same",
  "invalid.payment.type": "Invalid Payment Type",
  "merchant.user.already.exists": "Merchant User Already Exists",
  "email.already.taken": "Email already in use",
  "invalid.mobile.number": "Invalid Mobile Number",
  "to.account.is.empty": "Wallet number can’t be empty",
  "current.password.and.new.password.same": "Current Password And New Password Cannot be Same",
  "amount.field.is.empty": "Invalid Amount",
  "payment.type.and.template.field.miss.match": "Payment Type And Template Field Miss Match",
  "hierarchy.level.users.less.than.min.approval.count": "Hierarchy level users are less than minimum approval count",
  "contains.ekyc.incomplete.users": "This file contains EKYC incomplete users",
  "merchant.to.merchant.transfer.contains.customers": "Can\'t Send money to Customer Accounts using this feature",
  "password.expired.message": "Dear Pocket user, It has been %s days since you updated your Pocket PIN number. Please change your PIN number immediately to ensure safety & security.",
  "invalid.to.account.number": "Invalid To Wallet Number Found",
  "merchant.to.another.role": "Role change is not allowed from Merchant to another role",
  "another.to.merchant.role": "Role change to Merchant is not allowed from another role",
  "auth.token.required": "Auth Token Required",
  "batch.not.found": "Batch History Not Found."
}', 'en', '1.0');
