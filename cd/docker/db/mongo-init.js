db = db.getSiblingDB('sample_db')


db.createUser({
    user: 'admin',
    pwd: 'Password$4',
    roles: [
      {
        role: 'dbOwner',
      db: 'admin_db',
    },
  ],
});