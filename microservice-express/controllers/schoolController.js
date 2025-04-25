const School = require('../models/School');
const axios = require('axios');
const PDFDocument = require('pdfkit');
const fs = require('fs');
const path = require('path');

// URL du microservice Spring Boot des écoles
const SPRING_BOOT_URL = 'http://localhost:8090';

// Récupérer toutes les écoles depuis le microservice Spring Boot
exports.getAllSchools = async (req, res) => {
    try {
        const response = await axios.get(`${SPRING_BOOT_URL}/schools/all`);
        res.status(200).json(response.data);
    } catch (error) {
        console.error('Erreur lors de la récupération des écoles:', error);
        res.status(500).json({ message: 'Erreur lors de la récupération des écoles', error: error.message });
    }
};

// Récupérer une école par ID depuis le microservice Spring Boot
exports.getSchoolById = async (req, res) => {
    try {
        const response = await axios.get(`${SPRING_BOOT_URL}/schools/${req.params.id}`);
        res.status(200).json(response.data);
    } catch (error) {
        console.error('Erreur lors de la récupération de l\'école:', error);
        res.status(500).json({ message: 'Erreur lors de la récupération de l\'école', error: error.message });
    }
};

// Générer un PDF avec la liste des écoles
exports.generateSchoolsPDF = async (req, res) => {
    try {
        // Récupérer les écoles depuis le microservice Spring Boot
        const response = await axios.get(`${SPRING_BOOT_URL}/schools/all`);
        const schools = response.data;

        if (!schools || schools.length === 0) {
            return res.status(404).json({ message: 'Aucune école trouvée' });
        }

        // Créer un répertoire pour les PDFs s'il n'existe pas
        const pdfDir = path.join(__dirname, '../public/pdf');
        if (!fs.existsSync(pdfDir)) {
            fs.mkdirSync(pdfDir, { recursive: true });
        }

        // Nom du fichier PDF
        const fileName = `schools_${Date.now()}.pdf`;
        const filePath = path.join(pdfDir, fileName);

        // Créer un nouveau document PDF
        const doc = new PDFDocument({ margin: 50 });

        // Pipe le PDF vers un fichier et la réponse HTTP
        const stream = fs.createWriteStream(filePath);
        doc.pipe(stream);

        // Ajouter un titre
        doc.fontSize(25).text('Liste des Écoles', { align: 'center' });
        doc.moveDown();

        // Ajouter la date
        doc.fontSize(10).text(`Généré le: ${new Date().toLocaleDateString('fr-FR')}`, { align: 'right' });
        doc.moveDown();

        // Ajouter un tableau pour les écoles
        doc.fontSize(12);

        // En-têtes du tableau
        let yPos = 150;
        const tableTop = yPos;
        const tableLeft = 50;
        const colWidths = [200, 180, 100];

        // Dessiner l'en-tête du tableau
        doc.font('Helvetica-Bold')
            .text('Nom', tableLeft, yPos)
            .text('Adresse', tableLeft + colWidths[0], yPos)
            .text('Téléphone', tableLeft + colWidths[0] + colWidths[1], yPos);

        yPos += 20;
        doc.moveTo(tableLeft, yPos).lineTo(tableLeft + colWidths[0] + colWidths[1] + colWidths[2], yPos).stroke();
        yPos += 10;

        // Ajouter les données des écoles
        doc.font('Helvetica');

        schools.forEach(school => {
            // Vérifier s'il faut ajouter une nouvelle page
            if (yPos > 700) {
                doc.addPage();
                yPos = 50;

                // Redessiner l'en-tête du tableau sur la nouvelle page
                doc.font('Helvetica-Bold')
                    .text('Nom', tableLeft, yPos)
                    .text('Adresse', tableLeft + colWidths[0], yPos)
                    .text('Téléphone', tableLeft + colWidths[0] + colWidths[1], yPos);

                yPos += 20;
                doc.moveTo(tableLeft, yPos).lineTo(tableLeft + colWidths[0] + colWidths[1] + colWidths[2], yPos).stroke();
                yPos += 10;
                doc.font('Helvetica');
            }

            // Ajouter les données de l'école
            doc.text(school.name || '', tableLeft, yPos, { width: colWidths[0] - 10 });

            const address = school.address || '';

            doc.text(address, tableLeft + colWidths[0], yPos, { width: colWidths[1] - 10 });
            doc.text(school.phone || '', tableLeft + colWidths[0] + colWidths[1], yPos, { width: colWidths[2] - 10 });

            yPos += 25;
        });

        // Finaliser le PDF
        doc.end();

        // Attendre que le stream soit fermé
        stream.on('finish', () => {
            // Envoyer le fichier au client
            res.download(filePath, fileName, (err) => {
                if (err) {
                    console.error('Erreur lors de l\'envoi du PDF:', err);
                    return res.status(500).json({ message: 'Erreur lors de l\'envoi du PDF', error: err.message });
                }

                // Supprimer le fichier après envoi
                fs.unlink(filePath, (unlinkErr) => {
                    if (unlinkErr) console.error('Erreur lors de la suppression du fichier:', unlinkErr);
                });
            });
        });
    } catch (error) {
        console.error('Erreur lors de la génération du PDF:', error);
        res.status(500).json({ message: 'Erreur lors de la génération du PDF', error: error.message });
    }
};

// Recherche avancée des écoles
exports.searchSchools = async (req, res) => {
    try {
        // Critères de recherche depuis le corps de la requête
        const { name, city, country } = req.query;

        // Construire l'URL avec les paramètres de recherche
        let url = `${SPRING_BOOT_URL}/schools/search?`;
        if (name) url += `name=${encodeURIComponent(name)}&`;
        if (city) url += `city=${encodeURIComponent(city)}&`;
        if (country) url += `country=${encodeURIComponent(country)}&`;

        // Retirer le dernier '&' si présent
        url = url.endsWith('&') ? url.slice(0, -1) : url;

        const response = await axios.get(url);
        res.status(200).json(response.data);
    } catch (error) {
        console.error('Erreur lors de la recherche des écoles:', error);
        res.status(500).json({ message: 'Erreur lors de la recherche des écoles', error: error.message });
    }
}; 