/**
 * @file TemplateGalleryScreen.kt
 * @description S10 Template Gallery, rebuilt against the Lovable mockup
 *              "+ FAB Sheet · Template Gallery": sheet handle, kicker + serif
 *              question, docked search, category chips, a suggested row group,
 *              two-column brand grids and the pinned sheet actions.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS & DEPENDENCIES
 * 2. TYPES & CATEGORY LIST
 * 3. BRAND CATALOG
 * 4. MAIN SCREEN
 * 5. SUGGESTED ROWS & BRAND TILES
 * 6. TEMPLATE NAME LOOKUP
 */
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package org.zerokosh.app.ui.gallery

// #region Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import org.zerokosh.app.R
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.BrandTile
import org.zerokosh.app.ui.common.EmphasisSpan
import org.zerokosh.app.ui.common.GroupCard
import org.zerokosh.app.ui.common.Kicker
import org.zerokosh.app.ui.common.PrimaryPillButton
import org.zerokosh.app.ui.common.RowDivider
import org.zerokosh.app.ui.common.SectionLabel
import org.zerokosh.app.ui.common.SheetHandle
import org.zerokosh.app.ui.common.VaultListRow
import org.zerokosh.app.ui.theme.CornerCard
import org.zerokosh.app.ui.theme.VaultTheme
// #endregion

// #region Types & category list
private data class GalleryItem(
    val logoName: String,
    val title: String,
    val templateId: String,
    val presetName: String?,
    val category: String,
    val isPopular: Boolean = false,
)

private val GalleryCategories =
    listOf("Popular", "Banks", "UPI", "Cards", "Demat", "Gov ID", "Shopping", "Travel", "Utilities", "Apps")
// #endregion

// #region Brand catalog
private fun galleryCatalog(): List<GalleryItem> =
        listOf(
            // --- BANKS, NEO BANKS & NBFCS ---
            // Major Public Sector & Private Banks
            GalleryItem("HDFC Bank", "HDFC Bank Account", "bank_account", "HDFC Bank", "Banks", isPopular = true),
            GalleryItem("SBI", "State Bank of India (SBI)", "bank_account", "State Bank of India", "Banks", isPopular = true),
            GalleryItem("Axis Bank", "Axis Bank Account", "bank_account", "Axis Bank", "Banks", isPopular = true),
            GalleryItem("ICICI Bank", "ICICI Bank Account", "bank_account", "ICICI Bank", "Banks", isPopular = true),
            GalleryItem("Kotak Mahindra Bank", "Kotak Mahindra Bank", "bank_account", "Kotak Mahindra Bank", "Banks", isPopular = true),
            GalleryItem("Bank of Baroda", "Bank of Baroda (BOB)", "bank_account", "Bank of Baroda", "Banks", isPopular = true),
            GalleryItem("Punjab National Bank", "Punjab National Bank (PNB)", "bank_account", "Punjab National Bank", "Banks", isPopular = true),
            GalleryItem("Canara Bank", "Canara Bank Account", "bank_account", "Canara Bank", "Banks"),
            GalleryItem("Canara ai1", "Canara ai1 (Canara Bank)", "bank_account", "Canara Bank", "Banks"),
            GalleryItem("Union Bank of India", "Union Bank of India", "bank_account", "Union Bank of India", "Banks"),
            GalleryItem("Bank of India", "Bank of India (BOI)", "bank_account", "Bank of India", "Banks"),
            GalleryItem("Indian Bank", "Indian Bank Account", "bank_account", "Indian Bank", "Banks"),
            GalleryItem("Central Bank of India", "Central Bank of India", "bank_account", "Central Bank of India", "Banks"),
            GalleryItem("IndusInd Bank", "IndusInd Bank Account", "bank_account", "IndusInd Bank", "Banks"),
            GalleryItem("IDBI Bank", "IDBI Bank Account", "bank_account", "IDBI Bank", "Banks"),
            GalleryItem("Federal Bank", "Federal Bank Account", "bank_account", "Federal Bank", "Banks"),
            GalleryItem("IDFC FIRST Bank", "IDFC FIRST Bank", "bank_account", "IDFC FIRST Bank", "Banks"),
            GalleryItem("YES Bank", "YES Bank Account", "bank_account", "YES Bank", "Banks"),
            GalleryItem("RBL Bank", "RBL Bank Account", "bank_account", "RBL Bank", "Banks"),
            GalleryItem("Bandhan Bank", "Bandhan Bank Account", "bank_account", "Bandhan Bank", "Banks"),
            GalleryItem("South Indian Bank", "South Indian Bank", "bank_account", "South Indian Bank", "Banks"),
            GalleryItem("Karur Vysya Bank", "Karur Vysya Bank", "bank_account", "Karur Vysya Bank", "Banks"),
            GalleryItem("City Union Bank", "City Union Bank", "bank_account", "City Union Bank", "Banks"),
            GalleryItem("Jammu & Kashmir Bank", "J&K Bank Account", "bank_account", "Jammu & Kashmir Bank", "Banks"),
            GalleryItem("Karnataka Bank", "Karnataka Bank Account", "bank_account", "Karnataka Bank", "Banks"),
            GalleryItem("Tamilnad Mercantile Bank", "Tamilnad Mercantile (TMB)", "bank_account", "Tamilnad Mercantile Bank", "Banks"),
            GalleryItem("UCO Bank", "UCO Bank Account", "bank_account", "UCO Bank", "Banks"),
            GalleryItem("Bank of Maharashtra", "Bank of Maharashtra", "bank_account", "Bank of Maharashtra", "Banks"),
            GalleryItem("Punjab & Sind Bank", "Punjab & Sind Bank", "bank_account", "Punjab & Sind Bank", "Banks"),
            GalleryItem("Indian Overseas Bank", "Indian Overseas Bank (IOB)", "bank_account", "Indian Overseas Bank", "Banks"),
            GalleryItem("CSB Bank", "CSB Bank Account", "bank_account", "CSB Bank", "Banks"),
            GalleryItem("DCB Bank", "DCB Bank Account", "bank_account", "DCB Bank", "Banks"),
            GalleryItem("Dhanlaxmi Bank", "Dhanlaxmi Bank Account", "bank_account", "Dhanlaxmi Bank", "Banks"),
            GalleryItem("Nainital Bank", "Nainital Bank Account", "bank_account", "Nainital Bank", "Banks"),

            // Small Finance Banks
            GalleryItem("AU Small Finance Bank", "AU Small Finance Bank", "bank_account", "AU Small Finance Bank", "Banks"),
            GalleryItem("Equitas Small Finance Bank", "Equitas Small Bank", "bank_account", "Equitas Small Finance Bank", "Banks"),
            GalleryItem("Ujjivan Small Finance Bank", "Ujjivan Small Bank", "bank_account", "Ujjivan Small Finance Bank", "Banks"),
            GalleryItem("Jana Small Finance Bank", "Jana Small Bank", "bank_account", "Jana Small Finance Bank", "Banks"),
            GalleryItem("Suryoday Small Finance Bank", "Suryoday Small Bank", "bank_account", "Suryoday Small Finance Bank", "Banks"),
            GalleryItem("Utkarsh Small Finance Bank", "Utkarsh Small Bank", "bank_account", "Utkarsh Small Finance Bank", "Banks"),
            GalleryItem("ESAF Small Finance Bank", "ESAF Small Bank", "bank_account", "ESAF Small Finance Bank", "Banks"),
            GalleryItem("Capital Small Finance Bank", "Capital Small Bank", "bank_account", "Capital Small Finance Bank", "Banks"),
            GalleryItem("Shivalik Small Finance Bank", "Shivalik Small Bank", "bank_account", "Shivalik Small Finance Bank", "Banks"),
            GalleryItem("Unity Small Finance Bank", "Unity Small Bank", "bank_account", "Unity Small Finance Bank", "Banks"),

            // Payments Banks
            GalleryItem("Airtel Payments Bank", "Airtel Payments Bank", "bank_account", "Airtel Payments Bank", "Banks"),
            GalleryItem("Paytm Payments Bank", "Paytm Payments Bank", "bank_account", "Paytm Payments Bank", "Banks"),
            GalleryItem("India Post Payments Bank", "India Post Bank (IPPB)", "bank_account", "India Post Payments Bank", "Banks"),
            GalleryItem("Fino Payments Bank", "Fino Payments Bank", "bank_account", "Fino Payments Bank", "Banks"),
            GalleryItem("Jio Payments Bank", "Jio Payments Bank", "bank_account", "Jio Payments Bank", "Banks"),
            GalleryItem("NSDL Payments Bank", "NSDL Payments Bank", "bank_account", "NSDL Payments Bank", "Banks"),

            // Neo Banks & Fintechs
            GalleryItem("Fi Money", "Fi Money (Federal Bank)", "bank_account", "Fi Money", "Banks", isPopular = true),
            GalleryItem("Jupiter Money", "Jupiter (Federal Bank)", "bank_account", "Jupiter Money", "Banks", isPopular = true),
            GalleryItem("Niyo", "Niyo Global (SBM/DCB)", "bank_account", "Niyo Global", "Banks"),
            GalleryItem("Slice", "Slice Card (SBM)", "card", "Slice", "Banks"),
            GalleryItem("OneCard", "OneCard Credit Card", "card", "OneCard", "Banks"),
            GalleryItem("Uni Cards", "Uni Pay 1/3 Card", "card", "Uni Card", "Banks"),
            GalleryItem("FamApp", "FamApp (FamPay)", "card", "FamPay", "Banks"),
            GalleryItem("RazorpayX", "RazorpayX Business Bank", "bank_account", "RazorpayX", "Banks"),
            GalleryItem("Open Money", "Open Business Bank", "bank_account", "Open Money", "Banks"),
            GalleryItem("INDmoney", "INDmoney Stocks & Bank", "demat", "INDmoney", "Banks"),
            GalleryItem("Cheq", "Cheq Credit Card Bills", "card", "Cheq", "Banks"),

            // NBFCs & Housing Finance
            GalleryItem("Bajaj Finance", "Bajaj Finserv / Loan", "bank_account", "Bajaj Finance", "Banks", isPopular = true),
            GalleryItem("Tata Capital", "Tata Capital Loans", "bank_account", "Tata Capital", "Banks"),
            GalleryItem("Aditya Birla Capital", "Aditya Birla Capital", "bank_account", "Aditya Birla Capital", "Banks"),
            GalleryItem("Muthoot Finance", "Muthoot Gold Loan", "bank_account", "Muthoot Finance", "Banks"),
            GalleryItem("Manappuram Finance", "Manappuram Gold Loan", "bank_account", "Manappuram Finance", "Banks"),
            GalleryItem("L&T Finance", "L&T Finance Loans", "bank_account", "L&T Finance", "Banks"),
            GalleryItem("Mahindra Finance", "Mahindra Finance Loans", "bank_account", "Mahindra Finance", "Banks"),
            GalleryItem("Shriram Finance", "Shriram Finance Commercial", "bank_account", "Shriram Finance", "Banks"),
            GalleryItem("LIC Housing Finance", "LIC Housing (LICHFL)", "bank_account", "LIC Housing Finance", "Banks"),
            GalleryItem("PNB Housing Finance", "PNB Housing Finance", "bank_account", "PNB Housing Finance", "Banks"),
            GalleryItem("Navi", "Navi Personal Loans", "bank_account", "Navi Finserv", "Banks"),
            GalleryItem("KreditBee", "KreditBee Instant Loan", "bank_account", "KreditBee", "Banks"),
            GalleryItem("MoneyView", "MoneyView Personal Loan", "bank_account", "MoneyView", "Banks"),
            GalleryItem("IIFL Finance", "IIFL Finance Gold/Loan", "bank_account", "IIFL Finance", "Banks"),

            // Foreign Banks in India
            GalleryItem("Standard Chartered", "Standard Chartered Bank", "bank_account", "Standard Chartered Bank", "Banks"),
            GalleryItem("HSBC", "HSBC Bank India", "bank_account", "HSBC India", "Banks"),
            GalleryItem("Citibank", "Citibank India", "bank_account", "Citibank India", "Banks"),
            GalleryItem("DBS Bank", "DBS Bank India", "bank_account", "DBS Bank India", "Banks"),
            GalleryItem("Deutsche Bank", "Deutsche Bank India", "bank_account", "Deutsche Bank India", "Banks"),

            // --- UPI ---
            GalleryItem("UPI", "UPI VPA Handle", "upi", null, "UPI", isPopular = true),
            GalleryItem("BHIM App", "BHIM NPCI App", "upi", "BHIM", "UPI", isPopular = true),
            GalleryItem("Paytm", "Paytm UPI Account", "upi", "Paytm", "UPI", isPopular = true),
            GalleryItem("PhonePe", "PhonePe UPI Account", "upi", "PhonePe", "UPI", isPopular = true),
            GalleryItem("Google Pay", "Google Pay (GPay UPI)", "upi", "Google Pay", "UPI", isPopular = true),
            GalleryItem("Amazon Pay", "Amazon Pay Wallet & UPI", "upi", "Amazon Pay", "UPI", isPopular = true),
            GalleryItem("Cred", "Cred Pay UPI & Credit Card", "upi", "Cred", "UPI", isPopular = true),

            // --- CARDS ---
            GalleryItem("RuPay", "RuPay Debit / Credit Card", "card", "RuPay Card", "Cards", isPopular = true),
            GalleryItem("Visa", "Visa Debit / Credit Card", "card", "Visa Card", "Cards", isPopular = true),
            GalleryItem("Mastercard", "Mastercard Debit / Credit", "card", "Mastercard", "Cards", isPopular = true),
            GalleryItem("OneCard", "OneCard Metal Credit Card", "card", "OneCard", "Cards", isPopular = true),
            GalleryItem("Slice", "Slice Pay Card", "card", "Slice", "Cards"),
            GalleryItem("Uni Cards", "Uni Pay 1/3 Card", "card", "Uni Card", "Cards"),
            GalleryItem("Axis Bank", "Axis Credit Card", "card", "Axis Credit Card", "Cards"),
            GalleryItem("HDFC Bank", "HDFC Credit Card", "card", "HDFC Credit Card", "Cards"),
            GalleryItem("SBI", "SBI Credit Card", "card", "SBI Credit Card", "Cards"),
            GalleryItem("ICICI Bank", "ICICI Credit Card", "card", "ICICI Credit Card", "Cards"),

            // --- DEMAT ---
            GalleryItem("Zerodha", "Zerodha Broking", "demat", "Zerodha", "Demat", isPopular = true),
            GalleryItem("Kite by Zerodha", "Kite Zerodha Trading", "demat", "Zerodha", "Demat"),
            GalleryItem("Groww", "Groww Stocks & Mutual Funds", "demat", "Groww", "Demat", isPopular = true),
            GalleryItem("Angel One", "Angel One Broking", "demat", "Angel One", "Demat", isPopular = true),
            GalleryItem("Upstox", "Upstox Pro", "demat", "Upstox", "Demat"),
            GalleryItem("5Paisa", "5Paisa Capital", "demat", "5Paisa", "Demat"),
            GalleryItem("Fundzbazar", "Fundzbazar Mutual Funds", "demat", "Fundzbazar", "Demat"),
            GalleryItem("Dhan", "Dhan Stock Trading", "demat", "Dhan", "Demat"),
            GalleryItem("Geojit", "Geojit Financial Services", "demat", "Geojit", "Demat"),
            GalleryItem("Motilal Oswal", "Motilal Oswal Financial", "demat", "Motilal Oswal", "Demat"),
            GalleryItem("Paytm Money", "Paytm Money Investment", "demat", "Paytm Money", "Demat"),
            GalleryItem("SBI Securities", "SBI Securities (SSL)", "demat", "SBI Securities", "Demat"),
            GalleryItem("Axis Direct", "Axis Direct Trading", "demat", "Axis Direct", "Demat"),
            GalleryItem("ICICI Direct", "ICICI Direct 3-in-1 Account", "demat", "ICICI Direct", "Demat"),
            GalleryItem("Kotak Securities", "Kotak Securities Trade", "demat", "Kotak Securities", "Demat"),
            GalleryItem("Sharekhan", "Sharekhan Broking", "demat", "Sharekhan", "Demat"),

            // --- GOV ID ---
            GalleryItem("DigiLocker", "DigiLocker (Govt Documents)", "digilocker", "DigiLocker", "Gov ID", isPopular = true),
            GalleryItem("Aadhaar", "Aadhaar Card (UIDAI)", "aadhaar_card", "Aadhaar", "Gov ID", isPopular = true),
            GalleryItem("PAN", "PAN Card (Income Tax)", "pan_card", "PAN Card", "Gov ID", isPopular = true),
            GalleryItem("EPFO", "EPFO Universal Account (UAN)", "epf_pension", "EPFO", "Gov ID", isPopular = true),
            GalleryItem("Passport", "Indian Passport", "passport", "Passport", "Gov ID"),
            GalleryItem("Driving License", "Driving License (RTO)", "driving_license", "Driving License", "Gov ID"),
            GalleryItem("Voter ID", "Voter ID Card (ECI)", "voter_id", "Voter ID", "Gov ID"),

            // --- SHOPPING & FASHION ---
            GalleryItem("Myntra", "Myntra Fashion & Apparel", "shopping", "Myntra", "Shopping", isPopular = true),
            GalleryItem("Ajio", "Ajio Luxe & Fashion", "shopping", "Ajio", "Shopping", isPopular = true),
            GalleryItem("Flipkart", "Flipkart Online Shopping", "shopping", "Flipkart", "Shopping", isPopular = true),
            GalleryItem("Amazon", "Amazon India Shopping", "shopping", "Amazon", "Shopping", isPopular = true),
            GalleryItem("Amazon Business", "Amazon Business B2B", "shopping", "Amazon Business", "Shopping"),
            GalleryItem("Meesho", "Meesho Marketplace", "shopping", "Meesho", "Shopping", isPopular = true),
            GalleryItem("Alibaba", "Alibaba.com Wholesale", "login", "Alibaba", "Shopping"),
            GalleryItem("IndiaMART", "IndiaMART B2B Marketplace", "shopping", "IndiaMART", "Shopping"),
            GalleryItem("Nykaa", "Nykaa Cosmetics & Beauty", "shopping", "Nykaa", "Shopping", isPopular = true),
            GalleryItem("Nykaa Fashion", "Nykaa Fashion Apparel", "shopping", "Nykaa Fashion", "Shopping"),
            GalleryItem("Tata CLiQ", "Tata CLiQ & Luxury", "shopping", "Tata CLiQ", "Shopping"),
            GalleryItem("Snapdeal", "Snapdeal Shopping", "shopping", "Snapdeal", "Shopping"),
            GalleryItem("FirstCry", "FirstCry Baby & Kids Store", "shopping", "FirstCry", "Shopping"),
            GalleryItem("Reliance Digital", "Reliance Digital Electronics", "shopping", "Reliance Digital", "Shopping"),
            GalleryItem("Croma", "Croma Electronics Store", "shopping", "Croma", "Shopping"),
            GalleryItem("Vijay Sales", "Vijay Sales Electronics", "shopping", "Vijay Sales", "Shopping"),
            GalleryItem("Purplle", "Purplle Beauty Products", "shopping", "Purplle", "Shopping"),
            GalleryItem("Urbanic", "Urbanic Clothing", "shopping", "Urbanic", "Shopping"),
            GalleryItem("Westside", "Westside Store (Tata)", "shopping", "Westside", "Shopping"),
            GalleryItem("Max Fashion", "Max Fashion Clothing", "shopping", "Max Fashion", "Shopping"),
            GalleryItem("Zudio", "Zudio Fashion (Tata)", "shopping", "Zudio", "Shopping"),
            GalleryItem("Trends", "Trends (Reliance Trends)", "shopping", "Reliance Trends", "Shopping"),
            GalleryItem("Snitch", "Snitch Men's Fashion", "shopping", "Snitch", "Shopping"),
            GalleryItem("Beyoung", "Beyoung Apparel", "shopping", "Beyoung", "Shopping"),
            GalleryItem("Bewakoof", "Bewakoof Casual Wear", "shopping", "Bewakoof", "Shopping"),
            GalleryItem("FabIndia", "FabIndia Ethnic Craft", "shopping", "FabIndia", "Shopping"),
            GalleryItem("Decathlon", "Decathlon Sports Gear", "shopping", "Decathlon", "Shopping"),
            GalleryItem("Shoppers Stop", "Shoppers Stop Dept Store", "shopping", "Shoppers Stop", "Shopping"),
            GalleryItem("Lifestyle", "Lifestyle Fashion Stores", "shopping", "Lifestyle", "Shopping"),
            GalleryItem("H&M", "H&M Clothing", "shopping", "H&M", "Shopping"),
            GalleryItem("Zara", "Zara Fashion", "shopping", "Zara", "Shopping"),
            GalleryItem("Lenskart", "Lenskart Eyewear & Glasses", "shopping", "Lenskart", "Shopping"),
            GalleryItem("Titan", "Titan Watches", "shopping", "Titan", "Shopping"),
            GalleryItem("Tanishq", "Tanishq Gold & Jewellery", "shopping", "Tanishq", "Shopping"),
            GalleryItem("CaratLane", "CaratLane Jewellery", "shopping", "CaratLane", "Shopping"),
            GalleryItem("Pepperfry", "Pepperfry Furniture", "shopping", "Pepperfry", "Shopping"),
            GalleryItem("Urban Ladder", "Urban Ladder Furniture", "shopping", "Urban Ladder", "Shopping"),
            GalleryItem("Pernia's Pop up Shop", "Pernia's Pop-up Shop", "shopping", "Pernia's Pop up Shop", "Shopping"),

            // Dining & Food Outlets
            GalleryItem("Starbucks", "Starbucks India", "shopping", "Starbucks", "Shopping"),
            GalleryItem("abcoffee", "abcoffee Specialty Coffee", "shopping", "abcoffee", "Shopping"),
            GalleryItem("EazyDiner", "EazyDiner Restaurant Deals", "shopping", "EazyDiner", "Shopping"),
            GalleryItem("Keepa", "Keepa Amazon Price Tracker", "login", "Keepa", "Shopping"),

            // --- TRAVEL & TRANSPORTATION ---
            // Railways & Helplines
            GalleryItem("IRCTC", "IRCTC Rail Connect", "travel_booking", "IRCTC", "Travel", isPopular = true),
            GalleryItem("RailOne", "RailOne (Super App)", "travel_booking", "RailOne", "Travel", isPopular = true),
            GalleryItem("UTS", "UTS Unreserved Train Ticket", "travel_booking", "UTS", "Travel", isPopular = true),
            GalleryItem("NTES", "NTES Live Train Status", "login", "NTES", "Travel"),
            GalleryItem("RailMadad", "RailMadad Rail Helpline", "login", "RailMadad", "Travel"),
            GalleryItem("Where is my train", "Where is my Train", "login", "Where is my train", "Travel"),

            // Specific Metro Operators & Networks across India
            GalleryItem("Mumbai Metro One", "Mumbai Metro Line 1 (MMOPL)", "transit", "Mumbai Metro Line 1", "Travel", isPopular = true),
            GalleryItem("Maha Mumbai Metro", "Maha Mumbai Metro (Lines 2A/7)", "transit", "Maha Mumbai Metro", "Travel", isPopular = true),
            GalleryItem("Mumbai Metro 3", "Mumbai Metro Aqua Line 3 (MMRC)", "transit", "Mumbai Metro Line 3", "Travel"),
            GalleryItem("Navi Mumbai Metro", "Navi Mumbai Metro (CIDCO)", "transit", "Navi Mumbai Metro", "Travel"),
            GalleryItem("Mumbai Monorail", "Mumbai Monorail (MMRDA)", "transit", "Mumbai Monorail", "Travel"),
            GalleryItem("Thane Metro", "Thane Metro (MMRDA)", "transit", "Thane Metro", "Travel"),
            GalleryItem("Mumbai1", "Mumbai1 NCMC Mobility Card", "utility", "Mumbai1 Card", "Travel"),
            GalleryItem("DMRC", "Delhi Metro (DMRC)", "transit", "DMRC Metro", "Travel", isPopular = true),
            GalleryItem("Noida Metro", "Noida Metro (NMRC Aqua)", "transit", "Noida Metro", "Travel"),
            GalleryItem("Namma Metro", "Namma Metro (Bengaluru BMRCL)", "transit", "Namma Metro", "Travel"),
            GalleryItem("Pune Metro", "Pune Metro (Maha Metro)", "transit", "Pune Metro", "Travel"),
            GalleryItem("Nagpur Metro", "Nagpur Metro (Maha Metro)", "transit", "Nagpur Metro", "Travel"),
            GalleryItem("CMRL", "Chennai Metro (CMRL)", "transit", "Chennai Metro", "Travel"),
            GalleryItem("Kolkata Metro", "Kolkata Metro (RVNL)", "transit", "Kolkata Metro", "Travel"),
            GalleryItem("Hyderabad Metro", "Hyderabad Metro (L&T Metro)", "transit", "Hyderabad Metro", "Travel"),
            GalleryItem("Kochi Metro", "Kochi Metro (KMRL)", "transit", "Kochi Metro", "Travel"),
            GalleryItem("Ahmedabad Metro", "Ahmedabad Metro (GMRC)", "transit", "Ahmedabad Metro", "Travel"),
            GalleryItem("Lucknow Metro", "Lucknow Metro (UPMRC)", "transit", "Lucknow Metro", "Travel"),

            // State Road Transport Corporations (SRTCs & Municipal Buses)
            GalleryItem("MSRTC", "MSRTC Shivneri (Maharashtra)", "transit", "MSRTC", "Travel"),
            GalleryItem("GSRTC", "GSRTC Gurjarnagri (Gujarat)", "transit", "GSRTC", "Travel"),
            GalleryItem("KSRTC", "KSRTC Airavat (Karnataka)", "transit", "KSRTC Karnataka", "Travel"),
            GalleryItem("Kerala SRTC", "Kerala SRTC (SWIFT)", "transit", "Kerala SRTC", "Travel"),
            GalleryItem("UPSRTC", "UPSRTC Janrath (Uttar Pradesh)", "transit", "UPSRTC", "Travel"),
            GalleryItem("APSRTC", "APSRTC Amaravati (Andhra)", "transit", "APSRTC", "Travel"),
            GalleryItem("TSRTC", "TSRTC Garuda (Telangana)", "transit", "TSRTC", "Travel"),
            GalleryItem("HRTC", "HRTC Himgauri (Himachal)", "transit", "HRTC", "Travel"),
            GalleryItem("RSRTC", "RSRTC Express (Rajasthan)", "transit", "RSRTC", "Travel"),
            GalleryItem("BEST Bus", "BEST Bus (Mumbai Chalo)", "transit", "BEST Transit", "Travel"),
            GalleryItem("DTC Bus", "DTC Bus (Delhi Transit)", "transit", "DTC Bus", "Travel"),
            GalleryItem("BMTC Bus", "BMTC Bus (Bengaluru Namma BMTC)", "transit", "BMTC Bus", "Travel"),

            // Freight & Logistics / Courier Services
            GalleryItem("Porter", "Porter Intra-City Truck & Courier", "login", "Porter", "Travel"),
            GalleryItem("Delhivery", "Delhivery Courier & Parcel", "login", "Delhivery", "Travel"),
            GalleryItem("Borzo", "Borzo Same-Day Delivery", "login", "Borzo", "Travel"),

            // Travel & Cab / Flight / Bus Aggregators
            GalleryItem("RedBus", "RedBus (Bus & Train)", "travel_booking", "RedBus", "Travel", isPopular = true),
            GalleryItem("AbhiBus", "AbhiBus Ticket Booking", "travel_booking", "AbhiBus", "Travel"),
            GalleryItem("MakeMyTrip", "MakeMyTrip (Flight/Hotel/Train)", "travel_booking", "MakeMyTrip", "Travel", isPopular = true),
            GalleryItem("Ixigo", "Ixigo (Train & Flight Booking)", "travel_booking", "Ixigo", "Travel", isPopular = true),
            GalleryItem("Goibibo", "Goibibo Travel", "travel_booking", "Goibibo", "Travel"),
            GalleryItem("EaseMyTrip", "EaseMyTrip Flight Booking", "travel_booking", "EaseMyTrip", "Travel"),
            GalleryItem("Yatra", "Yatra Travel", "travel_booking", "Yatra", "Travel"),
            GalleryItem("Ola", "Ola Cabs & Auto", "travel_booking", "Ola Cabs", "Travel", isPopular = true),
            GalleryItem("Uber", "Uber Cabs & Moto", "travel_booking", "Uber", "Travel", isPopular = true),
            GalleryItem("Rapido", "Rapido Bike & Auto", "travel_booking", "Rapido", "Travel", isPopular = true),
            GalleryItem("Namma Yatri", "Namma Yatri Auto", "travel_booking", "Namma Yatri", "Travel"),
            GalleryItem("InDrive", "InDrive Fair Cabs", "travel_booking", "InDrive", "Travel"),
            GalleryItem("BluSmart", "BluSmart EV Cab", "travel_booking", "BluSmart", "Travel"),
            GalleryItem("FASTag", "FASTag NHAI Toll Pass", "utility", "FASTag", "Travel"),

            // --- UTILITIES ---
            GalleryItem("MyJio", "MyJio Recharge & Services", "telecom", "MyJio", "Utilities", isPopular = true),
            GalleryItem("Airtel", "Airtel Mobile & Fiber", "telecom", "Airtel", "Utilities", isPopular = true),
            GalleryItem("Jio", "Jio Mobile & AirFiber", "telecom", "Jio", "Utilities", isPopular = true),
            GalleryItem("Vi", "Vi (Vodafone Idea)", "telecom", "Vi", "Utilities"),
            GalleryItem("BSNL", "BSNL Mobile & FTTH", "telecom", "BSNL", "Utilities"),
            GalleryItem("MTNL", "MTNL Delhi & Mumbai", "telecom", "MTNL", "Utilities"),
            GalleryItem("Adani Electricity", "Adani Electricity Mumbai", "utility", "Adani Electricity", "Utilities", isPopular = true),
            GalleryItem("BEST Electricity", "BEST Electricity Mumbai", "utility", "BEST Electricity", "Utilities", isPopular = true),
            GalleryItem("Tata Power", "Tata Power Mumbai/Delhi", "utility", "Tata Power", "Utilities"),
            GalleryItem("MSEDCL", "MSEDCL (Mahadiscom)", "utility", "MSEDCL", "Utilities"),
            GalleryItem("BESCOM", "BESCOM (Bengaluru Power)", "utility", "BESCOM", "Utilities"),

            // --- GENERAL APPS, ECOSYSTEMS & HARDWARE ---
            // Major Suite Ecosystems (Global / Master Credentials Option)
            GalleryItem("Adobe", "Adobe Creative Cloud (Ecosystem)", "login", "Adobe Creative Cloud", "Apps", isPopular = true),
            GalleryItem("Microsoft", "Microsoft 365 / Account (Ecosystem)", "login", "Microsoft Account", "Apps", isPopular = true),
            GalleryItem("Proton", "Proton Ecosystem (Mail/VPN/Drive)", "login", "Proton Account", "Apps"),
            GalleryItem("Samsung", "Samsung Account (Ecosystem)", "login", "Samsung Account", "Apps", isPopular = true),
            GalleryItem("Apple", "Apple ID / iCloud (Ecosystem)", "login", "Apple ID", "Apps", isPopular = true),
            GalleryItem("Gmail", "Google Account (Ecosystem)", "login", "Google Account", "Apps", isPopular = true),

            // Adobe Suite Apps (Per-Service Individual Credentials Option)
            GalleryItem("Adobe Photoshop", "Adobe Photoshop", "login", "Adobe Photoshop", "Apps"),
            GalleryItem("Adobe Illustrator", "Adobe Illustrator", "login", "Adobe Illustrator", "Apps"),
            GalleryItem("Adobe Premiere", "Adobe Premiere Pro", "login", "Adobe Premiere", "Apps"),
            GalleryItem("Adobe Lightroom", "Adobe Lightroom", "login", "Adobe Lightroom", "Apps"),
            GalleryItem("Adobe Acrobat", "Adobe Acrobat PDF", "login", "Adobe Acrobat", "Apps"),

            // Microsoft Suite Apps & Operating Systems
            GalleryItem("OneDrive", "Microsoft OneDrive", "login", "Microsoft OneDrive", "Apps"),
            GalleryItem("Outlook", "Microsoft Outlook", "login", "Microsoft Outlook", "Apps"),
            GalleryItem("Teams", "Microsoft Teams", "login", "Microsoft Teams", "Apps"),
            GalleryItem("Xbox", "Microsoft Xbox Network", "login", "Microsoft Xbox", "Apps"),
            GalleryItem("Microsoft Copilot", "Microsoft Copilot", "login", "Microsoft Copilot", "Apps"),
            GalleryItem("Microsoft", "Windows PC License / Login", "login", "Windows License", "Apps"),

            // Proton Suite Apps
            GalleryItem("Proton Mail", "Proton Mail", "login", "Proton Mail", "Apps"),
            GalleryItem("Proton VPN", "Proton VPN", "login", "Proton VPN", "Apps"),
            GalleryItem("Proton Drive", "Proton Drive", "login", "Proton Drive", "Apps"),

            // Samsung Suite Apps
            GalleryItem("Samsung Wallet", "Samsung Wallet / Pay", "app_profile", "Samsung Wallet", "Apps"),
            GalleryItem("SmartThings", "Samsung SmartThings", "login", "SmartThings", "Apps"),

            // Hardware Brand Accounts
            GalleryItem("HP", "HP Account / Smart", "login", "HP Account", "Apps"),
            GalleryItem("Dell", "Dell Account", "login", "Dell Account", "Apps"),
            GalleryItem("Lenovo", "Lenovo ID", "login", "Lenovo ID", "Apps"),
            GalleryItem("Acer", "Acer ID", "login", "Acer ID", "Apps"),
            GalleryItem("ASUS", "ASUS Account", "login", "ASUS Account", "Apps"),
            GalleryItem("Oppo", "OPPO HeyTap Account", "login", "OPPO HeyTap", "Apps"),
            GalleryItem("Vivo", "Vivo Cloud Account", "login", "Vivo Account", "Apps"),
            GalleryItem("realme", "realme Account", "login", "realme Account", "Apps"),
            GalleryItem("Nothing", "Nothing Phone Account", "login", "Nothing Account", "Apps"),
            GalleryItem("OnePlus", "OnePlus Red Cable Club", "login", "OnePlus Account", "Apps"),
            GalleryItem("iQOO", "iQOO Account", "login", "iQOO Account", "Apps"),
            GalleryItem("Lava", "LAVA Account", "login", "LAVA Account", "Apps"),
            GalleryItem("itel", "itel Account", "login", "itel Account", "Apps"),
            GalleryItem("Motorola", "Motorola Account / Moto", "login", "Motorola Account", "Apps"),

            // Browsers & Privacy
            GalleryItem("Brave Browser", "Brave Sync & Rewards", "login", "Brave Sync", "Apps"),
            GalleryItem("Firefox", "Firefox Account / Sync", "login", "Firefox Sync", "Apps"),
            GalleryItem("DuckDuckGo", "DuckDuckGo Email Protection", "login", "DuckDuckGo Email", "Apps"),
            GalleryItem("Opera", "Opera Account / Sync", "login", "Opera Sync", "Apps"),

            // AI Tools & Cloud Storage
            GalleryItem("Notion", "Notion Workspace", "login", "Notion", "Apps", isPopular = true),
            GalleryItem("ElevenLabs", "ElevenLabs AI Voice", "login", "ElevenLabs", "Apps"),
            GalleryItem("Otter.ai", "Otter.ai Meeting Notes", "login", "Otter.ai", "Apps"),
            GalleryItem("ChatGPT", "ChatGPT (OpenAI)", "login", "ChatGPT", "Apps", isPopular = true),
            GalleryItem("Claude", "Claude (Anthropic)", "login", "Claude", "Apps"),
            GalleryItem("Perplexity", "Perplexity AI", "login", "Perplexity", "Apps"),
            GalleryItem("Manus AI", "Manus AI", "login", "Manus AI", "Apps"),
            GalleryItem("DeepSeek", "DeepSeek AI", "login", "DeepSeek", "Apps"),
            GalleryItem("Toingg", "Toingg AI Voice", "login", "Toingg AI", "Apps"),
            GalleryItem("AnyDesk", "AnyDesk Remote Desktop", "login", "AnyDesk", "Apps"),
            GalleryItem("Dropbox", "Dropbox Cloud Storage", "login", "Dropbox", "Apps"),
            GalleryItem("MEGA", "MEGA Cloud Storage", "login", "MEGA", "Apps"),
            GalleryItem("Canva", "Canva Design Account", "login", "Canva", "Apps"),

            // Social & Communication
            GalleryItem("Threads", "Threads (Meta)", "login", "Threads", "Apps"),
            GalleryItem("Truecaller", "Truecaller Account", "login", "Truecaller", "Apps", isPopular = true),
            GalleryItem("WhatsApp", "WhatsApp Messenger", "login", "WhatsApp", "Apps", isPopular = true),
            GalleryItem("Telegram", "Telegram Messenger", "login", "Telegram", "Apps", isPopular = true),
            GalleryItem("Discord", "Discord Community & Chat", "login", "Discord", "Apps"),
            GalleryItem("Arattai", "Arattai (Zoho Made-in-India Chat)", "login", "Arattai", "Apps"),
            GalleryItem("BitChat", "BitChat Messenger", "login", "BitChat", "Apps"),
            GalleryItem("Signal", "Signal Private Messenger", "login", "Signal", "Apps"),
            GalleryItem("Instagram", "Instagram Account", "login", "Instagram", "Apps", isPopular = true),
            GalleryItem("Facebook", "Facebook Account", "login", "Facebook", "Apps"),
            GalleryItem("Reddit", "Reddit Account", "login", "Reddit", "Apps"),
            GalleryItem("Snapchat", "Snapchat Account", "login", "Snapchat", "Apps"),
            GalleryItem("X", "X (formerly Twitter)", "login", "X", "Apps", isPopular = true),
            GalleryItem("Pinterest", "Pinterest Account", "login", "Pinterest", "Apps"),
            GalleryItem("LinkedIn", "LinkedIn Professional", "login", "LinkedIn", "Apps"),
            GalleryItem("Medium", "Medium Blogging", "login", "Medium", "Apps"),
            GalleryItem("Stack Overflow", "Stack Overflow / Stack", "login", "Stack Overflow", "Apps"),

            // Quick Commerce & Entertainment
            GalleryItem("Swiggy", "Swiggy Food & Instamart", "shopping", "Swiggy", "Apps", isPopular = true),
            GalleryItem("Zepto", "Zepto 10-Min Delivery", "shopping", "Zepto", "Apps", isPopular = true),
            GalleryItem("Zomato", "Zomato Food Delivery", "shopping", "Zomato", "Apps", isPopular = true),
            GalleryItem("District", "District by Zomato (Events)", "app_profile", "District", "Apps"),
            GalleryItem("Blinkit", "Blinkit Quick Delivery", "shopping", "Blinkit", "Apps", isPopular = true),
            GalleryItem("Bigbasket", "Bigbasket Grocery (Tata)", "shopping", "Bigbasket", "Apps", isPopular = true),
            GalleryItem("JioMart", "JioMart Grocery", "shopping", "JioMart", "Apps"),
            GalleryItem("MagicPin", "MagicPin Savings", "shopping", "MagicPin", "Apps"),
            GalleryItem("Justdial", "Justdial Local Search", "login", "Justdial", "Apps"),
            GalleryItem("Vyapar", "Vyapar Billing", "login", "Vyapar", "Apps"),
            GalleryItem("KNOT", "KNOT App", "login", "KNOT", "Apps"),
            GalleryItem("PVR", "PVR INOX Cinemas", "app_profile", "PVR", "Apps"),
            GalleryItem("BookMyShow", "BookMyShow Movie & Event Tickets", "app_profile", "BookMyShow", "Apps", isPopular = true),
            GalleryItem("Netflix", "Netflix Streaming", "login", "Netflix", "Apps", isPopular = true),
            GalleryItem("Disney Hotstar", "Disney+ Hotstar", "login", "Disney Hotstar", "Apps", isPopular = true),
            GalleryItem("Prime Video", "Amazon Prime Video", "login", "Prime Video", "Apps", isPopular = true),
            GalleryItem("JioCinema", "JioCinema Sports & Movies", "login", "JioCinema", "Apps"),
            GalleryItem("Spotify", "Spotify Music", "login", "Spotify", "Apps", isPopular = true),
            GalleryItem("YouTube", "YouTube Video & Music", "login", "YouTube", "Apps", isPopular = true)
        )
// #endregion

// #region Main screen
@Composable
fun TemplateGalleryScreen(
    app: ZerokoshApp,
    // brand is the organisation behind the entry — "HDFC Bank" for the HDFC
    // Credit Card, "Microsoft" for Xbox and Teams alike. It picks the logo and
    // it is what the record groups by.
    onPick: (templateId: String, presetName: String?, brand: String?) -> Unit,
    onClose: () -> Unit,
) {
    val c = VaultTheme.colors
    var category by rememberSaveable { mutableStateOf("Popular") }
    var query by rememberSaveable { mutableStateOf("") }
    val catalog = remember { galleryCatalog() }

    val matching = remember(catalog, query, category) {
        when {
            query.isNotBlank() -> catalog.filter {
                it.title.contains(query, true) || it.logoName.contains(query, true)
            }
            category == "Popular" -> catalog.filter { it.isPopular }
            else -> catalog.filter { it.category == category }
        }
    }
    // The mockup leads with a short "Suggested for you" row group, then falls
    // into the two-column brand grid.
    val suggested = if (query.isBlank() && category == "Popular") matching.take(3) else emptyList()
    val tiles = matching.drop(suggested.size)

    fun fieldCount(templateId: String): Int =
        app.catalog.templates.byId(templateId)?.fields?.size ?: 0

    Column(
        Modifier
            .fillMaxSize()
            .background(c.paper)
            .statusBarsPadding()
            .imePadding(),
    ) {
        Spacer(Modifier.height(10.dp))
        SheetHandle(Modifier.align(Alignment.CenterHorizontally))

        // Sheet header.
        Column(Modifier.padding(horizontal = 20.dp).padding(top = 8.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(Modifier.weight(1f)) {
                    Kicker("Templates")
                    Spacer(Modifier.height(4.dp))
                    Text(
                        buildAnnotatedString {
                            append("What are we ")
                            withStyle(EmphasisSpan) { append("storing?") }
                        },
                        style = MaterialTheme.typography.headlineMedium,
                        color = c.ink,
                    )
                }
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(c.ink(0.05f))
                        .clickable(onClick = onClose),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = c.ink(0.6f),
                        modifier = Modifier.size(20.dp),
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(c.surface)
                    .border(1.dp, c.line, CircleShape)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = null,
                    tint = c.ink(0.5f),
                    modifier = Modifier.size(18.dp),
                )
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    if (query.isEmpty()) {
                        Text(
                            "Search ${catalog.size} Indian services…",
                            style = MaterialTheme.typography.bodyMedium,
                            color = c.ink(0.45f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    BasicTextField(
                        value = query,
                        onValueChange = { query = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = c.ink),
                        cursorBrush = SolidColor(c.primary),
                    )
                }
            }
        }

        // Category chips.
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            GalleryCategories.forEach { cat ->
                val selected = cat == category && query.isBlank()
                Row(
                    Modifier
                        .height(32.dp)
                        .clip(CircleShape)
                        .background(if (selected) c.ink else Color.Transparent)
                        .then(if (selected) Modifier else Modifier.border(1.dp, c.line, CircleShape))
                        .clickable { category = cat; query = "" }
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    if (selected) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = null,
                            tint = c.paper,
                            modifier = Modifier.size(14.dp),
                        )
                    }
                    Text(
                        cat,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selected) c.paper else c.ink(0.7f),
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (suggested.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SectionLabel("Suggested for you", Modifier.weight(1f))
                        Text(
                            "Most-used first",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = c.accent,
                        )
                    }
                }
                item(span = { GridItemSpan(2) }) {
                    // Was a stacked GroupCard of list rows. The Expressive
                    // multi-browse carousel scales items as they scroll, so the
                    // shortlist reads as a shelf to browse rather than another
                    // list to read past -- and it stops the suggestions from
                    // pushing the full grid below the fold.
                    val carouselState = rememberCarouselState { suggested.size }
                    HorizontalMultiBrowseCarousel(
                        state = carouselState,
                        preferredItemWidth = 150.dp,
                        itemSpacing = 10.dp,
                        modifier = Modifier.fillMaxWidth().height(116.dp),
                    ) { index ->
                        val item = suggested[index]
                        Column(
                            Modifier
                                .fillMaxSize()
                                .maskClip(RoundedCornerShape(CornerCard))
                                .background(c.card)
                                .clickable(role = Role.Button) {
                                    onPick(item.templateId, item.presetName, item.logoName)
                                }
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            BrandTile(code = item.logoName, size = 40.dp)
                            Text(
                                item.title,
                                style = MaterialTheme.typography.labelLarge,
                                color = c.ink,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }

            if (tiles.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    SectionLabel(
                        if (query.isNotBlank()) "${tiles.size + suggested.size} matches"
                        else category,
                    )
                }
            }

            items(tiles, key = { it.title + it.category }) { item ->
                BrandGridTile(
                    item = item,
                    fields = fieldCount(item.templateId),
                    onClick = { onPick(item.templateId, item.presetName, item.logoName) },
                )
            }

            item(span = { GridItemSpan(2) }) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(CornerCard))
                        .background(c.accent.copy(alpha = 0.08f))
                        .border(1.dp, c.accent.copy(alpha = 0.15f), RoundedCornerShape(CornerCard))
                        .padding(16.dp),
                ) {
                    Text(
                        "Can’t find a service?",
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 12.5.sp,
                        color = c.ink,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Start blank and name your own fields — templates only pre-fill " +
                            "labels, never data.",
                        style = MaterialTheme.typography.bodySmall,
                        color = c.ink(0.65f),
                    )
                }
            }
        }

        // Pinned sheet actions.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(c.paper)
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PrimaryPillButton(
                label = "Blank template",
                onClick = { onPick("secure_note", null, null) },
                modifier = Modifier.weight(1f),
                showArrow = false,
            )
            Box(
                Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(c.primary.copy(alpha = 0.12f))
                    .clickable { onPick("card", null, null) },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Outlined.CreditCard,
                    contentDescription = "New card",
                    tint = c.primary,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}
// #endregion

// #region Brand grid tile
@Composable
private fun BrandGridTile(item: GalleryItem, fields: Int, onClick: () -> Unit) {
    val c = VaultTheme.colors
    val shape = RoundedCornerShape(CornerCard)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(c.card)
            .border(1.dp, c.ink(0.05f), shape)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        BrandTile(code = item.logoName)
        Column(Modifier.weight(1f)) {
            Text(
                item.presetName ?: item.title,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 15.sp,
                color = c.ink,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                if (fields > 0) "$fields fields" else "Template",
                fontSize = 10.5.sp,
                color = c.mute,
            )
        }
    }
}
// #endregion

// #region Template name lookup
/**
 * Display name per template. A map rather than a 21-arm `when`: the branches
 * carried no logic, only data, and as data the gaps were visible — shopping and
 * transit were showing the app-profile title even after gaining their own, and
 * travel_booking was falling through to "Secure note".
 *
 * The six government IDs deliberately share one title: on a list, "Government
 * ID" groups better than six near-identical names.
 */
private val TEMPLATE_TITLES: Map<String, Int> = mapOf(
    "bank_account" to R.string.tpl_bank_account,
    "card" to R.string.tpl_card,
    "upi" to R.string.tpl_upi,
    "demat" to R.string.tpl_demat,
    "insurance" to R.string.tpl_insurance,
    "gov_id" to R.string.tpl_gov_id,
    "pan_card" to R.string.tpl_gov_id,
    "aadhaar_card" to R.string.tpl_gov_id,
    "passport" to R.string.tpl_gov_id,
    "driving_license" to R.string.tpl_gov_id,
    "voter_id" to R.string.tpl_gov_id,
    "digilocker" to R.string.tpl_gov_id,
    "epf_pension" to R.string.tpl_epf_pension,
    "shopping" to R.string.tpl_shopping,
    "transit" to R.string.tpl_transit,
    "travel_booking" to R.string.tpl_travel_booking,
    "utility" to R.string.tpl_utility,
    "telecom" to R.string.tpl_telecom,
    "app_profile" to R.string.tpl_app_profile,
    "login" to R.string.tpl_login,
)

@Composable
fun templateName(templateId: String): String =
    stringResource(TEMPLATE_TITLES[templateId] ?: R.string.tpl_secure_note)
// #endregion
