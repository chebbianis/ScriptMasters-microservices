const express = require('express');
const router = express.Router();
const schoolController = require('../controllers/schoolController');

// Routes pour les écoles
router.get('/', schoolController.getAllSchools);
router.get('/search', schoolController.searchSchools);
router.get('/pdf', schoolController.generateSchoolsPDF);
router.get('/:id', schoolController.getSchoolById);

module.exports = router; 