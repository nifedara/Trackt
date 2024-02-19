**Trackt is an app to track your travels**.

    ![Travel image](https://res.cloudinary.com/dbtngbdgn/image/upload/v1706689562/Panama_2dc45fad-7790-4b19-89ac-96595805d30b.jpg)

    ---
    ### Auth
    To use the action menthods, you need to add a **token** to the `Authorization` header.

    To get a **token**:
    1. Create a User with [POST /Account/Create](#post_create)
    1. Get a **token** by calling [POST/Login](#post_login) with your **User** credentials

    ### Trips
    They are the heart of this app. **Users** can add their trips.
