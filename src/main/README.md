# Boring Inventory Manager
**Authored by Brandon Jones**

A straightforward inventory management tool built with JavaFX for the UI and Supabase for backend storage. It supports basic CRUD operations, user authentication, and theme switching.

---
### v1.0 Feature List

* Persistent storage via Supabase PostgreSQL database.
* User authentication.
* Basic CRUD operations.
* JavaFX user interface with light and dark themes via AtlantaFX.

### Planned Improvements for Future Versions

* Log all user actions, with a separate log view for each part number.
* Search across all part attributes, not just part number.
* Filtered table view display.
* Make certain fields optional.
* Redesign database schema to track parts both individually and categorically.

---
## Setup Instructions
### For Developers

1. Set up your development environment with Java 21 or later. I recommend Liberica Full JDK 21 as it includes all JavaFX dependencies.


2. Download Scene Builder (for visually editing .fxml files) and integrate it with your IDE.


3. Clone the repository:
```bash
git clone https://github.com/yourusername/boring-inventory-manager.git (replace with your actual repo URL).
```


4. Create a Supabase project and initialize the database table with this SQL command:
```sql 
CREATE TABLE inventory (partnumber TEXT PRIMARY KEY, testnumber INTEGER NOT NULL, serialnumber INTEGER NOT NULL, quantity INTEGER NOT NULL, allocation TEXT NOT NULL, purchaseorder INTEGER NOT NULL, description TEXT NOT NULL, location TEXT NOT NULL);
```

5. In ``src/util/DBUtil.java``, update ``SUPABASE_URL`` and ``SUPABASE_ANON_KEY`` with your Supabase credentials.


### For Users

1. Download the latest release from GitHub releases (currently v1.0).
2. Extract the .zip file to a folder on your computer.
3. Run ``BoringInventoryManager.exe``.
4. Log in using the provided credentials.

---
### License
#### MIT License

Copyright (c) 2025 Brandon Jones
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.